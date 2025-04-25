package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Basket;

import java.util.List;

public interface BasketService extends IService<Basket> {

    // 獲得購物車中有某商品的用戶，用於清除該用戶購物車的緩存
    List<String> listUserIdByProdId(Long prodId);

    // 刪除購物車緩存
    void removeShopCartItemsCacheByUserId(String userId);
}
