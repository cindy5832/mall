package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.dao.SkuMapper;
import com.demo.mall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "sku", key = "#skuId"),
            @CacheEvict(cacheNames = "skuList", key = "#prodId")
    })
    public void removeSkuCacheBySkuId(Long skuId, Long prodId) {

    }

    @Cacheable(cacheNames = "skuList", key = "#prodId")
    @Override
    public List<Sku> listByProdId(Long prodId) {
        return skuMapper.listByProdId(prodId);
    }

    @Override
    @Cacheable(cacheNames = "sku", key = "#skuId")
    public Sku getSkuBySkuId(Long skuId) {
        return skuMapper.selectById(skuId);
    }
}
