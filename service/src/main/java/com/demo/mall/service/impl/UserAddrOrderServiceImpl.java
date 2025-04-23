package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.UserAddrOrder;
import com.demo.mall.dao.UserAddrOrderMapper;
import com.demo.mall.service.UserAddrOrderService;
import org.springframework.stereotype.Service;

@Service
public class UserAddrOrderServiceImpl extends ServiceImpl<UserAddrOrderMapper, UserAddrOrder> implements UserAddrOrderService {
}
