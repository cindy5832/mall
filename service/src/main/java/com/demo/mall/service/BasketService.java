package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.ShopCartDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.param.OrderItemParam;
import com.demo.mall.bean.model.Basket;

import java.util.List;

public interface BasketService extends IService<Basket> {

    // 獲得購物車中有某商品的用戶，用於清除該用戶購物車的緩存
    List<String> listUserIdByProdId(Long prodId);

    // 刪除購物車緩存
    void removeShopCartItemsCacheByUserId(String userId);

    // 獲取用戶提交的購物車商品項目
    List<ShopCartItemDto> getShopCartItemsByOrderItems(List<Long> basketIds, OrderItemParam orderItem, String userId);

    // 根據商店組裝購物車中的商品訊息，返回每個商店中的購物車商品訊息
    List<ShopCartDto> getShopCarts(List<ShopCartItemDto> shopCartItems);

}
