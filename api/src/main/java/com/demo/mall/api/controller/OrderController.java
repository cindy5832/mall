package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.demo.mall.bean.app.dto.*;
import com.demo.mall.bean.app.param.OrderParam;
import com.demo.mall.bean.app.param.OrderShopParam;
import com.demo.mall.bean.app.param.SubmitOrderParam;
import com.demo.mall.bean.event.ConfirmOrderEvent;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.model.UserAddr;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/p/order")
@Tag(name = "api-order", description = "訂單")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserAddrService userAddrService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/confirm")
    @Operation(summary = "order-confirm", description = "生成訂單")
    public ServerResponseEntity<ShopCartOrderMergerDto> confirm(@Valid @RequestBody OrderParam orderParam) {
        String userId = SecurityUtils.getUser().getUserId();
        // 訂單的地址訊息
        UserAddr userAddr = userAddrService.getUserAddrByUserId(orderParam.getAddrId(), userId);
        UserAddrDto userAddrDto = BeanUtil.copyProperties(userAddr, UserAddrDto.class);

        // 組裝獲取用戶提交的購物車商品項
        List<ShopCartItemDto> shopCartItems = basketService.getShopCartItemsByOrderItems(orderParam.getBasketIds(), orderParam.getOrderItem(), userId);

        if (CollectionUtil.isEmpty(shopCartItems)) {
            throw new ShopException("請選擇所需要的商品加入購物車");
        }

        // 根據商店組裝購物車中的商訊息，返回每個商店中的購物車訊息
        List<ShopCartDto> shopCarts = basketService.getShopCarts(shopCartItems);

        // 將要返回給前端的完整訂單訊息
        ShopCartOrderMergerDto shopCartOrderMergerDto = new ShopCartOrderMergerDto();
        shopCartOrderMergerDto.setUserAddr(userAddrDto);

        // 所有商店的訂單訊息
        List<ShopCartOrderDto> shopCartOrders = new ArrayList<>();

        double actualTotal = 0.0;
        double total = 0.0;
        int totalCount = 0;
        double orderReduce = 0.0;

        for (ShopCartDto shopCart : shopCarts) {
            // 每個商店的訂單訊息
            ShopCartOrderDto shopCartOrder = new ShopCartOrderDto();
            shopCartOrder.setShopId(shopCart.getShopId());
            shopCartOrder.setShopName(shopCart.getShopName());

            List<ShopCartItemDiscountDto> shopCartItemDiscounts = shopCart.getShopCartItemDiscounts();

            // 商店中的所有商品訊息
            List<ShopCartItemDto> shopAllShopCartItems = new ArrayList<>();
            for (ShopCartItemDiscountDto shopCartItemDiscount : shopCartItemDiscounts) {
                List<ShopCartItemDto> discountShopCartItems = shopCartItemDiscount.getShopCartItems();
                shopAllShopCartItems.addAll(discountShopCartItems);
            }

            shopCartOrder.setShopCartItemDiscounts(shopCartItemDiscounts);

            applicationContext.publishEvent(new ConfirmOrderEvent(shopCartOrder, orderParam, shopAllShopCartItems));

            actualTotal = Arith.add(actualTotal, shopCartOrder.getActualTotal());
            total = Arith.add(total, shopCartOrder.getTotal());
            totalCount = totalCount + shopCartOrder.getTotalCount();
            orderReduce = Arith.add(orderReduce, shopCartOrder.getShopReduce());
            shopCartOrders.add(shopCartOrder);
        }

        shopCartOrderMergerDto.setActualTotal(actualTotal);
        shopCartOrderMergerDto.setTotal(total);
        shopCartOrderMergerDto.setTotalCount(totalCount);
        shopCartOrderMergerDto.setOrderReduce(orderReduce);
        shopCartOrderMergerDto.setShopCartOrders(shopCartOrders);

        shopCartOrderMergerDto = orderService.putConfirmOrderCache(userId, shopCartOrderMergerDto);

        return ServerResponseEntity.success(shopCartOrderMergerDto);
    }

    @PostMapping("/submit")
    @Operation(summary = "order-submit", description = "根據傳入參數判斷是否為購物車提交訂單，同時將購物車刪除，用戶進行付款；返回支付流水號")
    public ServerResponseEntity<OrderNumbersDto> submit(@Valid @RequestBody SubmitOrderParam submitOrderParam) {
        String userId = SecurityUtils.getUser().getUserId();
        ShopCartOrderMergerDto mergerOrder = orderService.getConfirmOrderCache(userId);
        if (mergerOrder == null) {
            throw new ShopException("訂單已過期，請重新下單");
        }

        List<OrderShopParam> orderShopParams = submitOrderParam.getOrderShopParam();

        List<ShopCartOrderDto> shopCartOrders = mergerOrder.getShopCartOrders();

        // 設置備註
        if (CollectionUtil.isEmpty(orderShopParams)) {
            for (ShopCartOrderDto shopCartOrder : shopCartOrders) {
                for (OrderShopParam orderShopParam : orderShopParams) {
                    if (Objects.equals(shopCartOrder.getShopId(), orderShopParam.getShopId())) {
                        shopCartOrder.setRemarks(orderShopParam.getRemarks());
                    }
                }
            }
        }

        List<Order> orders = orderService.submit(userId, mergerOrder);
        StringBuilder orderNumbers = new StringBuilder();
        for (Order order : orders) {
            orderNumbers.append(order.getOrderNumber()).append(",");
        }
        orderNumbers.deleteCharAt(orderNumbers.length() - 1);

        boolean isShopCartOrder = false;
        // 移除緩存
        for (ShopCartOrderDto shopCartOrder : shopCartOrders) {
            for (ShopCartItemDiscountDto shopCartItemDiscount : shopCartOrder.getShopCartItemDiscounts()) {
                for (ShopCartItemDto shopCartItem : shopCartItemDiscount.getShopCartItems()) {
                    Long basketId = shopCartItem.getBasketId();
                    if (basketId != null && basketId != 0) {
                        isShopCartOrder = true;
                    }
                    skuService.removeSkuCacheBySkuId(shopCartItem.getSkuId(), shopCartItem.getProdId());
                    productService.removeProductCacheByProdId(shopCartItem.getProdId());
                }
            }
        }
        // 購物車提交訂單時 (即有購物車ID)
        if (isShopCartOrder) {
            basketService.removeShopCartItemsCacheByUserId(userId);
        }
        orderService.removeConfirmOrderCache(userId);
        return  ServerResponseEntity.success(new OrderNumbersDto(orderNumbers.toString()));
    }
}
