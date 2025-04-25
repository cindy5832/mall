package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.UserAddr;
import com.demo.mall.dao.UserAddrMapper;
import com.demo.mall.service.UserAddrService;
import org.springframework.stereotype.Service;

@Service
public class UserAddrServiceImpl extends ServiceImpl<UserAddrMapper, UserAddr> implements UserAddrService {
}
