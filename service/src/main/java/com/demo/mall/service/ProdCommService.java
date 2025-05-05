package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.ProdCommDataDto;
import com.demo.mall.bean.app.dto.ProdCommDto;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.utils.PageParam;

public interface ProdCommService extends IService<ProdComm> {

    // 根據參數分頁獲取商品評價
    IPage<ProdComm> getProdCommPage(PageParam<ProdComm> page, ProdComm prodComm);

    // 根據商品id獲取商品評論訊息
    ProdCommDataDto getProdCommDataByProdId(Long prodId);

    // 根據用戶id分頁獲取商品評論
    IPage<ProdCommDto> getProdCommDtoPageByUserId(PageParam page, String userId);

    // 根據商品id和評價等級獲取商品評價
    IPage<ProdCommDto> getProdCommDtoPageByProdId(PageParam page, Long prodId, Integer evaluate);
}
