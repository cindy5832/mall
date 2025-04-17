package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.ProdPropValue;
import com.demo.mall.dao.ProdPropValueMapper;
import com.demo.mall.service.ProdPropValueService;
import org.springframework.stereotype.Service;

@Service
public class ProdPropValueServiceImpl extends ServiceImpl<ProdPropValueMapper, ProdPropValue> implements ProdPropValueService {
}
