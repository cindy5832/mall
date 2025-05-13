package com.demo.mall.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.system.constant.Constant;
import com.demo.mall.system.dao.SysMenuMapper;
import com.demo.mall.system.dao.SysRoleMenuMapper;
import com.demo.mall.system.model.SysMenu;
import com.demo.mall.system.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> listMenuByUserId(Long userId) {
        // 用戶的所有菜單訊息
        List<SysMenu> sysMenus = new ArrayList<>();
        // 系統管理員 擁有最高權限
        if (userId == Constant.SUPER_ADMIN_ID) {
            sysMenus = sysMenuMapper.listMenu();
        } else {
            sysMenus = sysMenuMapper.listMenuByUserId(userId);
        }

        Map<Long, List<SysMenu>> sysMenuLevelMap = sysMenus.stream()
                .sorted(Comparator.comparing(SysMenu::getOrderNum))
                .collect(Collectors.groupingBy(SysMenu::getParentId));
        // 一級菜單
        List<SysMenu> rootMenu = sysMenuLevelMap.get(0L);
        if (CollectionUtil.isEmpty(rootMenu)) {
            return Collections.emptyList();
        }
        // 二級菜單
        rootMenu.forEach(sysMenu -> {
            sysMenu.setList(sysMenuLevelMap.get(sysMenu.getMenuId()));
        });
        return rootMenu;
    }

    @Override
    public List<SysMenu> listMenuAndBtn() {
        return sysMenuMapper.listMenuAndBtn();
    }

    @Override
    public List<SysMenu> listRootMenu() {
        return sysMenuMapper.listRootMenu();
    }

    @Override
    public List<SysMenu> listSimpleMenuNoButton() {
        return sysMenuMapper.listSimpleMenuNoButton();
    }

    @Override
    public List<SysMenu> listChildrenMenuByParentId(Long parentId) {
        return sysMenuMapper.listChildrenMenuByParentId(parentId);
    }

    @Override
    public void deleteMenuAndRoleMenu(Long menuId) {
        // 刪除菜單
        this.removeById(menuId);
        // 刪除菜單與角色關聯
        sysRoleMenuMapper.deleteByMenuId(menuId);
    }

    @Override
    public List<Long> listMenuByRoleId(Long roleId) {
        return sysMenuMapper.listMenuIdByRoleId(roleId);
    }
}
