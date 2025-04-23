package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Sku;

public interface SkuService extends IService<Sku> {

    // 根據商品id和skId刪除緩存
    void removeSkuCacheBySkuId(Long skuId, Long prodId);

}
