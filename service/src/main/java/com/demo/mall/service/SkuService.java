package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Sku;

import java.util.List;

public interface SkuService extends IService<Sku> {

    // 根據商品id和skId刪除緩存
    void removeSkuCacheBySkuId(Long skuId, Long prodId);

    // 根據商品id獲取商品中的Sku列表
    List<Sku> listByProdId(Long prodId);

    // 根據skuId獲取sku訊息
    Sku getSkuBySkuId(Long skuId);
}
