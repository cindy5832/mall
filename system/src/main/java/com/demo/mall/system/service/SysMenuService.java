package com.demo.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.system.model.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    // 獲取用戶菜單列表
    List<SysMenu> listMenuByUserId(Long userId);

    // 獲取菜單和按鈕列表
    List<SysMenu> listMenuAndBtn();

    // 獲得一級菜單
    List<SysMenu> listRootMenu();

    // 獲得簡單的menu tree 用於ele-ui tree顯示 根據orderNum排序
    List<SysMenu> listSimpleMenuNoButton();

    // 根據一菜菜單id獲取二級菜單
    List<SysMenu> listChildrenMenuByParentId(Long parentId);

    // 刪除菜單與角色之間的關係
    void deleteMenuAndRoleMenu(Long menuId);

    // 根據角色id 獲得菜單列表
    List<Long> listMenuByRoleId(Long roleId);
}
