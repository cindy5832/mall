package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Category;
import com.demo.mall.dao.CategoryBrandMapper;
import com.demo.mall.dao.CategoryMapper;
import com.demo.mall.dao.CategoryPropMapper;
import com.demo.mall.service.CategoryService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.ObjLongConsumer;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryPropMapper categoryPropMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public List<Category> tableCategory(Long shopId) {
        return categoryMapper.tableCategory(shopId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(Category category) {
        category.setRecTime(new Date());
        categoryMapper.insert(category);
        insertBrandsAndAttributes(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByCategory(Category category) {
        Category dbCategory = categoryMapper.selectById(category.getCategoryId());
        category.setUpdateTime(new Date());
        // 保存分類訊息
        categoryMapper.updateById(category);
        // 先刪除舊的再增加
        deleteBrandsAndAttributes(category.getCategoryId());
        insertBrandsAndAttributes(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        categoryMapper.deleteById(categoryId);
        deleteBrandsAndAttributes(categoryId);
    }

    @Override
    public List<Category> treeSelect(Long shopId, int grade) {
        List<Category> categories = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .le(Category::getGrade, grade)
                        .eq(Category::getShopId, shopId)
        );
        return categoryListToTree(categories);
    }

    @Override
    public List<Category> listByParentId(Long parentId) {
        return categoryMapper.listByParentId(parentId);
    }

    private List<Category> categoryListToTree(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return Lists.newArrayList();
        }
        Map<Long, List<Category>> categoryMap = categories.stream().collect(
                Collectors.groupingBy(Category::getParentId)
        );
        List<Category> categoryList = categoryMap.get(0L);
        transformCategoryTree(categoryList, categoryMap);
        return categoryList;
    }

    private void transformCategoryTree(List<Category> categoryList, Map<Long, List<Category>> categoryMap) {
        for (Category category : categoryList) {
            List<Category> nextList = categoryMap.get(category.getParentId());
            if(CollectionUtils.isEmpty(nextList)){
                continue;
            }
            // 將排序好的list設為下一個層級
            category.setCategories(nextList);
            // 处理下个层级
            transformCategoryTree(nextList, categoryMap);
        }

    }

    private void deleteBrandsAndAttributes(Long categoryId) {
        // 刪除所有關聯品牌
        categoryBrandMapper.deleteByCategoryId(categoryId);
        // 刪除所有分類所相關的參數
        categoryPropMapper.deleteByCategoryId(categoryId);
    }

    private void insertBrandsAndAttributes(Category category) {
        // 保存分類與品牌訊息
        if (CollUtil.isNotEmpty(category.getBrandIds())) {
            categoryMapper.insertCategoryBrand(category.getCategoryId(), category.getBrandIds());
        }
        // 保存分類與參數訊息
        if (CollUtil.isNotEmpty(category.getAttributeIds())) {
            categoryPropMapper.insertCategoryProp(category.getCategoryId(), category.getAttributeIds());
        }
    }
}
