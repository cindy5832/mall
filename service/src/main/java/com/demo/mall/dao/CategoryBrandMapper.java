package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.CategoryBrand;

public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {

    // 根據品牌名稱刪除分類品牌
    void deletByBrandId(Long brandId);
}
