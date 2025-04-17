package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.CategoryBrand;
import com.demo.mall.dao.CategoryBrandMapper;
import com.demo.mall.service.CategoryBrandService;
import org.springframework.stereotype.Service;

@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {
}
