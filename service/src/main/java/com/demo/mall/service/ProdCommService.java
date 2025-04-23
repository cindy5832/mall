package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.utils.PageParam;

public interface ProdCommService extends IService<ProdComm> {

    // 根據參數分頁獲取商品評價
    IPage<ProdComm> getProdCommPage(PageParam<ProdComm> page, ProdComm prodComm);
}
