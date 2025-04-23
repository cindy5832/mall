package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.PickAddr;
import com.demo.mall.dao.PickAddrMapper;
import com.demo.mall.service.PickAddrService;
import org.springframework.stereotype.Service;

@Service
public class PickAddrServiceImpl extends ServiceImpl<PickAddrMapper, PickAddr> implements PickAddrService {
}
