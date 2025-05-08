package com.demo.mall.api.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.demo.mall.bean.app.dto.ShopCartAmountDto;
import com.demo.mall.bean.app.dto.ShopCartDto;
import com.demo.mall.bean.app.dto.ShopCartItemDiscountDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.param.ChangeShopCartParam;
import com.demo.mall.bean.app.param.ShopCartParam;
import com.demo.mall.bean.event.ShopCartEvent;
import com.demo.mall.bean.model.Basket;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.BasketService;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.SkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/p/shopCart")
@Tag(name = "shopCart", description = "購物車")
public class ShopCartController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/info")
    @Operation(summary = "shopCart-info", description = "獲取用戶購物車訊息，參數為用戶選中的活動項數組，以購物車id為key")
    public ServerResponseEntity<List<ShopCartDto>> info(@RequestBody Map<Long, ShopCartParam> basketIdShopCartParamMap) {
        String userId = SecurityUtils.getUser().getUserId();
        // 更新購物車訊息
        if (MapUtil.isNotEmpty(basketIdShopCartParamMap)) {
            basketService.updateBasketByShopCartParam(userId, basketIdShopCartParamMap);
        }
        // 獲取購物車所有Item
        List<ShopCartItemDto> shopCartItems = basketService.getShopCartItems(userId);
        return ServerResponseEntity.success(basketService.getShopCarts(shopCartItems));
    }

    @DeleteMapping("/deleteItem")
    @Operation(summary = "delete-item", description = "通過購物車id刪除用戶購物車物品")
    public ServerResponseEntity deleteItem(@RequestBody List<Long> basketIds) {
        String userId = SecurityUtils.getUser().getUserId();
        basketService.deleteShopCartItemsByBasketIds(userId, basketIds);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "delete-all", description = "清空購物車所有物品")
    public ServerResponseEntity<String> deleteAll() {
        String userId = SecurityUtils.getUser().getUserId();
        basketService.deleteAllShopCartItems(userId);
        return ServerResponseEntity.success("Delete success!!");
    }

    @PostMapping("/changeItem")
    @Operation(summary = "change-item", description = "通過商品id、skuId、shopId，添加/修改用戶購物車商品，並傳入改變的商品個數(count)")
    // 當count > 0 增加商品數量；count < 0 減去商品數量； 最終Count < 0 則將商品從購物車中刪除
    public ServerResponseEntity<String> addItem(@Valid @RequestBody ChangeShopCartParam param) {
        if (param.getCount() == 0) {
            return ServerResponseEntity.showFailMsg("請輸入更改數量");
        }
        String userId = SecurityUtils.getUser().getUserId();
        List<ShopCartItemDto> shopCartItems = basketService.getShopCartItems(userId);
        Product productParam = productService.getProductByProdId(param.getProdId());
        Sku skuParam = skuService.getSkuBySkuId(param.getSkuId());

        // 當商品狀態不正常時，不能添加到購物車
        if (productParam.getStatus() != 1 || skuParam.getStatus() != 1) {
            return ServerResponseEntity.showFailMsg("當前商品已下架");
        }
        for (ShopCartItemDto shopCartItem : shopCartItems) {
            if (Objects.equals(param.getSkuId(), shopCartItem.getSkuId())) {
                Basket basket = new Basket();
                basket.setUserId(userId);
                basket.setBasketCount(param.getCount() + shopCartItem.getProdCount());
                basket.setBasketId(shopCartItem.getBasketId());

                // 避免購物車數量為負
                if (basket.getBasketCount() <= 0) {
                    basketService.deleteShopCartItemsByBasketIds(userId, Collections.singletonList(basket.getBasketId()));
                    return ServerResponseEntity.success();
                }

                // 當sku實際庫存不足時，無法添加到購物車中
                if (skuParam.getStocks() < basket.getBasketCount() && shopCartItem.getProdCount() > 0) {
                    return ServerResponseEntity.showFailMsg("庫存不足");
                }
                basketService.updateShopCartItem(basket);
                return ServerResponseEntity.success();
            }
        }

        // 防止購物車已被刪除情況下，添加了負數的商品
        if (param.getCount() < 0) {
            return ServerResponseEntity.showFailMsg("商品已從購物車刪除");
        }
        // 當sku實際庫存不足時，不能添加到購物車
        if (skuParam.getStocks() < param.getCount()) {
            return ServerResponseEntity.showFailMsg("庫存不足");
        }
        // 正常更新
        basketService.addShopCartItem(param, userId);
        return ServerResponseEntity.success("添加成功");
    }

    @GetMapping("/prodCount")
    @Operation(summary = "total-product-count", description = "獲取所有購物車商品數量")
    public ServerResponseEntity<Integer> prodCount() {
        String userId = SecurityUtils.getUser().getUserId();
        List<ShopCartItemDto> shopCartItemDtoList = basketService.getShopCartItems(userId);
        if (CollectionUtil.isEmpty(shopCartItemDtoList)) {
            return ServerResponseEntity.success(0);
        }
        Integer totalCount = shopCartItemDtoList.stream().map(ShopCartItemDto::getProdCount).reduce(0, Integer::sum);
        return ServerResponseEntity.success(totalCount);
    }

    @DeleteMapping("/cleanExpireProdList")
    @Operation(summary = "clear-expired-prod", description = "清空用戶已失效的商品")
    public ServerResponseEntity cleanExpireProdList() {
        String userId = SecurityUtils.getUser().getUserId();
        basketService.cleanExpiryProdList(userId);
        return ServerResponseEntity.success();
    }

    @PostMapping("/totalPay")
    @Operation(summary = "total-pay", description = "獲取選中購物項總計、商品數量，參數為購物車id數組")
    public ServerResponseEntity<ShopCartAmountDto> getToalPay(@RequestBody List<Long> basketIds) {
        // 獲取購物車的所有Items
        List<ShopCartItemDto> dbShopCartItems = basketService.getShopCartItems(SecurityUtils.getUser().getUserId());
        List<ShopCartItemDto> chooseShopCartItems = dbShopCartItems
                .stream()
                .filter(shopCartItemDto -> {
                    for (Long basketId : basketIds) {
                        if (Objects.equals(shopCartItemDto.getBasketId(), basketId)) {
                            return true;
                        }
                    }
                    return false;
                }).toList();

        // 根據商店id區分Item
        Map<Long, List<ShopCartItemDto>> shopCartMap = chooseShopCartItems.stream().collect(Collectors.groupingBy(ShopCartItemDto::getShopId));

        double total = 0.0;
        int count = 0;
        double reduce = 0.0;
        for (Long shopId : shopCartMap.keySet()) {
            // 獲取商店所有的商品項
            List<ShopCartItemDto> shopCartItemDtoList = shopCartMap.get(shopId);
            // 創建每個商店的購物車訊息
            ShopCartDto shopCart = new ShopCartDto();
            shopCart.setShopId(shopId);

            applicationContext.publishEvent(new ShopCartEvent(shopCart, shopCartItemDtoList));

            List<ShopCartItemDiscountDto> shopCartItemDiscounts = shopCart.getShopCartItemDiscounts();
            for (ShopCartItemDiscountDto shopCartItemDiscount : shopCartItemDiscounts) {
                List<ShopCartItemDto> shopCartItems = shopCartItemDiscount.getShopCartItems();

                for (ShopCartItemDto shopCartItem : shopCartItems) {
                    count += shopCartItem.getProdCount();
                    total = Arith.add(shopCartItem.getProductTotalAmount(), total);
                }
            }
        }
        ShopCartAmountDto shopCartAmountDto = new ShopCartAmountDto();
        shopCartAmountDto.setCount(count);
        shopCartAmountDto.setTotalMoney(total);
        shopCartAmountDto.setSubtractMoney(reduce);
        shopCartAmountDto.setFinalMoney(Arith.sub(total, reduce));
        return ServerResponseEntity.success(shopCartAmountDto);
    }
}
