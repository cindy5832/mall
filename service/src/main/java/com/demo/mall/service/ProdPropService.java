package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.ProdProp;

public interface ProdPropService extends IService<ProdProp> {

    // 獲取屬性和屬性值
    IPage<ProdProp> pagePropAndValue(ProdProp prodProp, Page<ProdProp> page);

    // 保存屬性 & 屬性值
    void saveProdProdAndValues(ProdProp prodProp);

    // 更新屬性 & 屬性值
    void updateProdPropAndValues(ProdProp prodProp);

    // 刪除屬性、屬性值
    void deleteProdPropAndValues(Long propId, Integer propRule, Long shopId);
}
