package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Brand;
import com.demo.mall.dao.BrandMapper;
import com.demo.mall.dao.CategoryBrandMapper;
import com.demo.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Brand getByBrandName(String brandName) {
        return baseMapper.selectByBrandName(brandName);
    }

    @Override
    public void deleteByBrand(Long brandId) {
        brandMapper.deleteById(brandId);
        categoryBrandMapper.deletByBrandId(brandId);
    }
}
