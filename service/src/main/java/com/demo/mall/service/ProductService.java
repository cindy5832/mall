package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Product;

public interface ProductService extends IService<Product> {

    // 根據商品id獲取商品訊息
    Product getProductByProdId(Long prodId);

    // 根據商品id刪除緩存
    void removeProductCacheByProdId(Long prodId);

    // 保存商品
    void saveProduct(Product product);

    // 更新商品
    void updateProduct(Product product, Product dbProduct);

    // 根據商品id刪除商品
    void removeProductByProdId(Long prodId);
}
