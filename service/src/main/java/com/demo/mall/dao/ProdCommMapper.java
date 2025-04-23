package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.utils.PageParam;
import org.apache.ibatis.annotations.Param;

public interface ProdCommMapper extends BaseMapper<ProdComm> {

    // 根據參數分頁獲取商品評論
    IPage<ProdComm> getProdCommPage(PageParam<ProdComm> page,@Param("prodComm") ProdComm prodComm);
}
