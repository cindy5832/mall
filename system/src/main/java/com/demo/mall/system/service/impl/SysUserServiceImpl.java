package com.demo.mall.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.system.dao.SysUserMapper;
import com.demo.mall.system.dao.SysUserRoleMapper;
import com.demo.mall.system.model.SysUser;
import com.demo.mall.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUser getByUserName(String userName) {
        return sysUserMapper.selectByUsername(userName);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserMapper.queryAllPerms(userId);
    }

    @Override
    public SysUser getSysUserById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
    public void updatePasswordByUserId(Long userId, String newPassword) {
        SysUser sysUser = new SysUser();
        sysUser.setPassword(newPassword);
        sysUser.setUserId(userId);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserAndUserRole(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        sysUserMapper.insert(sysUser);
        if (CollUtil.isEmpty(sysUser.getRoleIdList())) {
            return;
        }
        sysUserRoleMapper.insertUserAndUserRole(sysUser.getUserId(), sysUser.getRoleIdList());
    }

    @Override
    public void updateUserAndUserRole(SysUser user) {
        // 更新用戶
        sysUserMapper.updateById(user);
        // 先刪除用戶與角色關係
        sysUserRoleMapper.deleteByUserId(user.getUserId());
        if(CollUtil.isEmpty(user.getRoleIdList())) {
            return;
        }
        // 保存用戶與角色關係
        sysUserRoleMapper.insertUserAndUserRole(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userIds, Long shopId) {
        sysUserMapper.deletBatch(userIds, shopId);
    }
}
