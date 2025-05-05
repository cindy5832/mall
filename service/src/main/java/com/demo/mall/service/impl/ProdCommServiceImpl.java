package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.app.dto.ProdCommDataDto;
import com.demo.mall.bean.app.dto.ProdCommDto;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.utils.Arith;
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

    @Override
    public ProdCommDataDto getProdCommDataByProdId(Long prodId) {
        ProdCommDataDto prodCommDataDto = prodCommMapper.getProdCommDataByProdId(prodId);
        // 計算好評率
        if (prodCommDataDto.getPraiseNumber() == 0 || prodCommDataDto.getNumber() == 0) {
            prodCommDataDto.setPositiveRating(0.0);
        } else {
            prodCommDataDto.setPositiveRating(Arith.mul(Arith.div(prodCommDataDto.getPraiseNumber(), prodCommDataDto.getNumber()), 100));
        }
        return prodCommDataDto;
    }

    @Override
    public IPage<ProdCommDto> getProdCommDtoPageByUserId(PageParam page, String userId) {
        return prodCommMapper.getProdCommDtoPageByUserId(page, userId);
    }

    @Override
    public IPage<ProdCommDto> getProdCommDtoPageByProdId(PageParam page, Long prodId, Integer evaluate) {
        IPage<ProdCommDto> prodCommDtos = prodCommMapper.getProdCommDtoPageByProdId(page, prodId, evaluate);
        prodCommDtos.getRecords().forEach(
                prodCommDto -> {
                    // 匿名評價
                    if (prodCommDto.getIsAnonymous() == 1){
                        prodCommDto.setNickName(null);
                    }
                }
        );
        return prodCommDtos;
    }
}
