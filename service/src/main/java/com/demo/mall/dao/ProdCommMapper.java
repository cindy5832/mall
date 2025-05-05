package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.mall.bean.app.dto.ProdCommDataDto;
import com.demo.mall.bean.app.dto.ProdCommDto;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.utils.PageParam;
import org.apache.ibatis.annotations.Param;

public interface ProdCommMapper extends BaseMapper<ProdComm> {

    // 根據參數分頁獲取商品評論
    IPage<ProdComm> getProdCommPage(PageParam<ProdComm> page, @Param("prodComm") ProdComm prodComm);

    // 根據商品id獲取商品評論訊息
    ProdCommDataDto getProdCommDataByProdId(@Param("prodId") Long prodId);

    // 根據用戶id分頁獲取評論
    IPage<ProdCommDto> getProdCommDtoPageByUserId(PageParam page, @Param("userId") String userId);

    // 根據評價等級和商品id分頁獲取商品評價
    IPage<ProdCommDto> getProdCommDtoPageByProdId(@Param("page") Page page, @Param("prodId") Long prodId, @Param("evaluate") Integer evaluate);
}
