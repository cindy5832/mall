package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Brand;

public interface BrandService extends IService<Brand> {
    // 根據品牌名稱獲取品牌訊息
    Brand getByBrandName(String brandName);

    // 刪除 品牌 及 品牌分類表
    void deleteByBrand(Long brandId);
}
