package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Brand;

public interface BrandMapper extends BaseMapper<Brand> {

    // 根據品牌名稱獲取品牌
    Brand selectByBrandName(String brandName);
}
