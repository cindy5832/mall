package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.ProdPropValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdPropValueMapper extends BaseMapper<ProdPropValue> {

    // 插入商品屬性數值
    void insertPropValues(@Param("propId") Long propId, @Param("prodPropValues") List<ProdPropValue> prodPropValues);

    // 刪除屬性值
    void deleteByPropId(@Param("propId") Long propId);

}
