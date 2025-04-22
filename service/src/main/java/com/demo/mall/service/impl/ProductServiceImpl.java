package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Product;
import com.demo.mall.dao.ProductMapper;
import com.demo.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Cacheable(cacheNames = "product", key = "#prodId")
    public Product getProductByProdId(Long prodId) {
        return productMapper.selectById(prodId);
    }
}
