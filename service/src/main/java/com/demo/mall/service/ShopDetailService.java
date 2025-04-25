package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.ShopDetail;

public interface ShopDetailService extends IService<ShopDetail> {

    // 根據商店id刪除商店詳情訊息緩存
    void removeShopDetailCacheByShopId(Long shopId);

    // 根據商店id獲取商店訊息
    ShopDetail getShopDetailByShopId(Long shopId);

    // 更新商店訊息
    void updateShopDetail(ShopDetail shopDetail);

    // 刪除商店詳情
    void deleteShopDetailByShopId(Long shopId);
}
