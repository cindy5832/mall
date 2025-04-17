package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.CategoryProp;
import com.demo.mall.dao.CategoryPropMapper;
import com.demo.mall.service.CategoryPropService;
import org.springframework.stereotype.Service;

@Service
public class CategoryPropServiceImpl extends ServiceImpl<CategoryPropMapper, CategoryProp> implements CategoryPropService {
}
