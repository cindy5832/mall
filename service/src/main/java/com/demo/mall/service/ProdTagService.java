package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.ProdTag;

import java.util.List;

public interface ProdTagService extends IService<ProdTag> {

    // 刪除商品分組標籤緩存
    void removeProdTag();

    // 獲取商品分組標籤列表
    List<ProdTag> listProdTag();
}
