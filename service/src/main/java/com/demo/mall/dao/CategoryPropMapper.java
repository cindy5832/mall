package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.CategoryProp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryPropMapper extends BaseMapper<CategoryProp> {

    // 根據屬性id 刪除分類屬性
    void deleteByPropId(Long propId);

    // 插入分類屬性
    void insertCategoryProp(@Param("categoryId") Long categoryId, @Param("propIds")List<Long> attributeIds);

    // 根據分類id刪除分類屬性
    void deleteByCategoryId(Long categoryId);
}
