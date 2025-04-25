package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.ProdTagReference;

import java.util.List;

public interface ProdTagReferenceService extends IService<ProdTagReference> {

    // 根據id獲取標籤Id
    List<Long> listTagIdByProdId(Long prodId);
}
