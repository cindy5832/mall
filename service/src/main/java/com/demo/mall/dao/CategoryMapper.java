package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    // 根據商店Id獲取分類列表
    List<Category> tableCategory(Long shopId);

    // 增加分類品牌
    void insertCategoryBrand(@Param("categoryId") Long categoryId, @Param("brandIds") List<Long> brandIds);

    // 根據parentId獲得分類列表
    List<Category> listByParentId(Long parentId);
}
