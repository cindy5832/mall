package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Basket;
import com.demo.mall.dao.BasketMapper;
import com.demo.mall.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceImpl extends ServiceImpl<BasketMapper, Basket> implements BasketService {

    @Autowired
    private BasketMapper basketMapper;

    @Override
    public List<String> listUserIdByProdId(Long prodId) {
        return basketMapper.listUserIdsByProdId(prodId);
    }

    @Override
    @CacheEvict(cacheNames = "ShopCartItems", key = "#userId")
    public void removeShopCartItemsCacheByUserId(String userId) {

    }
}
