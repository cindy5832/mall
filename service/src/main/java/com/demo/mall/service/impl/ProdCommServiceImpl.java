package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.dao.ProdCommMapper;
import com.demo.mall.service.ProdCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {

    @Autowired
    private ProdCommMapper prodCommMapper;

    @Override
    public IPage<ProdComm> getProdCommPage(PageParam<ProdComm> page, ProdComm prodComm) {
        return prodCommMapper.getProdCommPage(page, prodComm);
    }
}
