package com.demo.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.system.dao.SysRoleMapper;
import com.demo.mall.system.dao.SysRoleMenuMapper;
import com.demo.mall.system.dao.SysUserRoleMapper;
import com.demo.mall.system.model.SysRole;
import com.demo.mall.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleAndRoleMenu(SysRole role) {
        role.setCreateTime(new Date());
        this.save(role);
        if (CollectionUtil.isEmpty(role.getMenuIdList())) {
            return;
        }
        // 保存角色與菜單關係
        sysRoleMenuMapper.insertRoleAndRoleMenu(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleAndRoleMenu(SysRole role) {
        // 更新角色
        sysRoleMapper.updateById(role);
        // 先刪除學色與菜單關聯
        sysRoleMenuMapper.deleteBatch(new Long[]{
                role.getRoleId()
        });
        if (CollectionUtil.isEmpty(role.getMenuIdList())) {
            return;
        }
        // 保存角色與菜單關聯
        sysRoleMenuMapper.insertRoleAndRoleMenu(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        // 刪除角色
        sysRoleMapper.deleteBatch(ids);

        // 刪除角色與菜單 關聯
        sysRoleMenuMapper.deleteBatch(ids);

        // 刪除角色與用戶關聯
        sysUserRoleMapper.deleteBatch(ids);
    }

    @Override
    public List<Long> listRoleIdByUserId(Long userId) {
        return sysRoleMapper.listRoleIdByUserId(userId);
    }
}
