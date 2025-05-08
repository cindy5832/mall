package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.app.dto.ShopCartDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.param.ChangeShopCartParam;
import com.demo.mall.bean.app.param.OrderItemParam;
import com.demo.mall.bean.app.param.ShopCartParam;
import com.demo.mall.bean.event.ShopCartEvent;
import com.demo.mall.bean.model.Basket;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.ShopDetail;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.common.utils.CacheManagerUtil;
import com.demo.mall.dao.BasketMapper;
import com.demo.mall.service.BasketService;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.ShopDetailService;
import com.demo.mall.service.SkuService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl extends ServiceImpl<BasketMapper, Basket> implements BasketService {

    @Autowired
    private BasketMapper basketMapper;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopDetailService shopDetailService;

    @Autowired
    private CacheManagerUtil cacheManagerUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<String> listUserIdByProdId(Long prodId) {
        return basketMapper.listUserIdsByProdId(prodId);
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void removeShopCartItemsCacheByUserId(String userId) {

    }

    @Override
    public List<ShopCartItemDto> getShopCartItemsByOrderItems(List<Long> basketIds, OrderItemParam orderItem, String userId) {
        if (orderItem == null && CollectionUtil.isEmpty(basketIds)) {
            return Collections.emptyList();
        }

        // 當立即購物時，未提交訂單是沒有購物車訊息
        if (CollectionUtil.isEmpty(basketIds) && orderItem != null) {
            Sku sku = skuService.getSkuBySkuId(orderItem.getSkuId());
            if (sku == null) {
                throw new RuntimeException("訂單含有無法辨識的商品");
            }
            Product prod = productService.getProductByProdId(orderItem.getProdId());
            if (prod == null) {
                throw new RuntimeException("訂單包含無法辨識的商品");
            }

            // 獲取購物車所有Item
            ShopCartItemDto shopCartItemDto = new ShopCartItemDto();
            shopCartItemDto.setBasketId(-1L);
            shopCartItemDto.setSkuId(orderItem.getSkuId());
            shopCartItemDto.setProdCount(orderItem.getProdCount());
            shopCartItemDto.setProdId(orderItem.getProdId());
            shopCartItemDto.setSkuName(sku.getSkuName());
            shopCartItemDto.setPic(StrUtil.isBlank(sku.getPic()) ? prod.getPic() : sku.getPic());
            shopCartItemDto.setProdName(sku.getProdName());
            shopCartItemDto.setProductTotalAmount(Arith.mul(sku.getPrice(), orderItem.getProdCount()));
            shopCartItemDto.setPrice(sku.getPrice());
            shopCartItemDto.setDistributionCardNo(orderItem.getDistributionCardNo());
            shopCartItemDto.setBasketDate(new Date());
            ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(orderItem.getShopId());
            shopCartItemDto.setShopId(shopDetail.getShopId());
            shopCartItemDto.setShopName(shopDetail.getShopName());
            return Collections.singletonList(shopCartItemDto);
        }

        List<ShopCartItemDto> dbShopCartItems = getShopCartItems(userId);

        return dbShopCartItems
                .stream()
                .filter(shopCartItemDto -> basketIds.contains(shopCartItemDto.getBasketId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopCartDto> getShopCarts(List<ShopCartItemDto> shopCartItems) {
        // 根據商店id劃分item
        Map<Long, List<ShopCartItemDto>> shopCartMap = shopCartItems.stream().collect(Collectors.groupingBy(ShopCartItemDto::getShopId));

        // 返回一個商店的所有訊息
        List<ShopCartDto> shopCartDtos = Lists.newArrayList();
        for (Long shopId : shopCartMap.keySet()) {
            // 獲取商店的所有商品項
            List<ShopCartItemDto> shopCartItemDtoList = shopCartMap.get(shopId);

            // 構建每個商店的購物車訊息
            ShopCartDto shopCart = new ShopCartDto();
            // 商店訊息
            shopCart.setShopId(shopId);
            shopCart.setShopName(shopCartItemDtoList.get(0).getShopName());

            applicationContext.publishEvent(new ShopCartEvent(shopCart, shopCartItemDtoList));

            shopCartDtos.add(shopCart);
        }
        return shopCartDtos;
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void updateBasketByShopCartParam(String userId, Map<Long, ShopCartParam> basketIdShopCartParamMap) {
        basketMapper.updateDiscountItemId(userId, basketIdShopCartParamMap);
    }

    public List<ShopCartItemDto> getShopCartItems(String userId) {
        // 調用緩存訊息
        List<ShopCartItemDto> shopCartItemDtoList = cacheManagerUtil.getCache("ShopCartItems", userId);
        if (shopCartItemDtoList != null) return shopCartItemDtoList;
        shopCartItemDtoList = basketMapper.getShopCartItems(userId);
        for (ShopCartItemDto shopCartItemDto : shopCartItemDtoList) {
            shopCartItemDto.setProductTotalAmount(Arith.mul(shopCartItemDto.getProdCount(), shopCartItemDto.getPrice()));
        }
        cacheManagerUtil.putCache("ShopCartItems", userId, shopCartItemDtoList);
        return shopCartItemDtoList;
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void deleteShopCartItemsByBasketIds(String userId, List<Long> basketIds) {
        basketMapper.deleteShopCartItemsByBasketIds(userId, basketIds);
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void deleteAllShopCartItems(String userId) {
        basketMapper.deleteAllShopCartItems(userId);
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#basket.userId")
    public void updateShopCartItem(Basket basket) {
        basketMapper.updateById(basket);
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void addShopCartItem(ChangeShopCartParam param, String userId) {
        Basket basket = new Basket();
        basket.setBasketCount(param.getCount());
        basket.setBasketDate(new Date());
        basket.setProdId(param.getProdId());
        basket.setShopId(param.getShopId());
        basket.setUserId(userId);
        basket.setSkuId(param.getSkuId());
        basket.setDistributionCardNo(param.getDistributionCardNo());
        basketMapper.insert(basket);
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void cleanExpiryProdList(String userId) {
        basketMapper.cleanExpiryProdList(userId);
    }
}
