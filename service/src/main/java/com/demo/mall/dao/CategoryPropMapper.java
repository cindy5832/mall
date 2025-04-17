package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.CategoryProp;

public interface CategoryPropMapper extends BaseMapper<CategoryProp> {

    // 根據屬性id 刪除分類屬性
    void deleteByPropId(Long propId);
}
