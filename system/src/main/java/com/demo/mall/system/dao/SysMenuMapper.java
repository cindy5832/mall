package com.demo.mall.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.system.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    // 獲取系統的所有菜單id
    List<SysMenu> listMenu();

    // 查詢用戶的所有菜單id
    List<SysMenu> listMenuByUserId(Long userId);

    // 獲取菜單和按鈕列表
    List<SysMenu> listMenuAndBtn();

    // 獲得一級菜單
    List<SysMenu> listRootMenu();

    // 獲取簡單的menu tree 用於ele-ui tree 顯示，根據OrderNUm排序
    List<SysMenu> listSimpleMenuNoButton();

    // 根據一級菜單id獲取二級菜單
    List<SysMenu> listChildrenMenuByParentId(Long parentId);

    // 根據角色id獲得菜單列表
    List<Long> listMenuIdByRoleId(Long roleId);
}
