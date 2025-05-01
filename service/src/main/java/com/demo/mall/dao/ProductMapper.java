package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ProductMapper extends BaseMapper<Product> {

    // 返回庫存
    void returnStock(@Param("prodCollect") Map<Long, Integer> prodCollect);
}
