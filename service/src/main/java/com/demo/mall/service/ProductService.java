package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.ProductDto;
import com.demo.mall.bean.app.dto.TagProductDto;
import com.demo.mall.bean.model.Product;
import com.demo.mall.common.utils.PageParam;

import java.util.List;

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

    // 根據分類id分頁獲取商品列表
    IPage<ProductDto> pageByCategoryId(PageParam<ProductDto> page, Long categoryId);

    // 根據上架時間倒敘分頁獲取商品
    IPage<ProductDto> pageByPutAwayTime(PageParam<ProductDto> page);

    // 根據標籤分頁獲取商品
    IPage<ProductDto> pageByTagId(PageParam<ProductDto> page, Long tagId);

    // 分頁獲取銷售量較高的商品
    IPage<ProductDto> moreBuyProdList(PageParam<ProductDto> page);

    // 分頁獲取商品列表
    List<TagProductDto> tagProdList();

    // 分頁獲取收藏商品
    IPage<ProductDto> collectionProds(PageParam page, String userId);
}
