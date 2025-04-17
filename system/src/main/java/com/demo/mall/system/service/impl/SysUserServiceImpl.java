package com.demo.mall.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.system.dao.SysUserMapper;
import com.demo.mall.system.model.SysUser;
import com.demo.mall.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByUserName(String userName) {
        return sysUserMapper.selectByUsername(userName);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserMapper.queryAllPerms(userId);
    }
}
