package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.dao.SkuMapper;
import com.demo.mall.service.SkuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "sku", key = "#skuId"),
            @CacheEvict(cacheNames = "skuList", key = "#prodId")
    })
    public void removeSkuCacheBySkuId(Long skuId, Long prodId) {

    }
}
