package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Product;

public interface ProductService extends IService<Product> {

    // 根據商品id獲取商品訊息
    Product getProductByProdId(Long prodId);
}
