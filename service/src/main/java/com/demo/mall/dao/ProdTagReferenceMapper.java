package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.ProdTagReference;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdTagReferenceMapper extends BaseMapper<ProdTagReference> {

    // 根據屬性獲取標籤id
    List<Long> listTagIdByProdId(@Param("prodId") Long prodId);

    // 插入標籤
    void insertBatch(@Param("shopId") Long shopId, @Param("prodId") Long prodId, @Param("tagList") List<Long> tagList);
}
