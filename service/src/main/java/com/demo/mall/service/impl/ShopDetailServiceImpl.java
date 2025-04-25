package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.ShopDetail;
import com.demo.mall.dao.ShopDetailMapper;
import com.demo.mall.service.ShopDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopDetailServiceImpl extends ServiceImpl<ShopDetailMapper, ShopDetail> implements ShopDetailService {

    @Autowired
    private ShopDetailMapper shopDetailMapper;

    @Override
    @CacheEvict(cacheNames = "shop_detail", key = "#shopId")
    public void removeShopDetailCacheByShopId(Long shopId) {

    }

    @Override
    @Cacheable(cacheNames = "shop_detail", key = "#shopId")
    public ShopDetail getShopDetailByShopId(Long shopId) {
        return shopDetailMapper.selectById(shopId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "shop-detail", key = "#shopDetail.shopId")
    public void updateShopDetail(ShopDetail shopDetail) {
        shopDetailMapper.updateById(shopDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "shop-detail", key = "#shopId")
    public void deleteShopDetailByShopId(Long shopId) {
        shopDetailMapper.deleteById(shopId);
    }
}
