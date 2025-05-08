package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.app.dto.ProductDto;
import com.demo.mall.bean.app.dto.TagProductDto;
import com.demo.mall.bean.model.Product;
import com.demo.mall.common.utils.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper extends BaseMapper<Product> {

    // 返回庫存
    void returnStock(@Param("prodCollect") Map<Long, Integer> prodCollect);

    // 根據分類id分頁獲取商品
    IPage<ProductDto> pageByCategoryId(PageParam<ProductDto> page, @Param("categoryId") Long categoryId);

    // 根據上架時間分頁獲取商品
    IPage<ProductDto> pagePutAwayTime(PageParam<ProductDto> page);

    // 根據標籤id 獲取商品訊息
    IPage<ProductDto> pageByTagId(PageParam<ProductDto> page, Long tagId);

    // 分頁獲取銷售量最多的商品
    IPage<ProductDto> moreBuyProdList(PageParam<ProductDto> page);

    // 獲取分組商品列表
    List<TagProductDto> tagProdList();

    // 獲取用戶的收藏商品列表
    IPage<ProductDto> collectionProds(@Param("page") PageParam page, @Param("userId") String userId);
}
