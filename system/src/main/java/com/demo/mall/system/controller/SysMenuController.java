package com.demo.mall.system.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.system.constant.Constant;
import com.demo.mall.system.constant.MenuType;
import com.demo.mall.system.model.SysMenu;
import com.demo.mall.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/sys/menu")
@Tag(name = "sys-menu", description = "系統菜單")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/nav")
    @Operation(summary = "sys-menu-nav", description = "根據userId獲取用戶所擁有的菜單和權限")
    public ServerResponseEntity<Map<Object, Object>> nav() {
        List<SysMenu> sysMenus = sysMenuService.listMenuByUserId(SecurityUtils.getSysUser().getUserId());
        return ServerResponseEntity.success(
                MapUtil.builder().put("menuList", sysMenus)
                        .put("authorities", SecurityUtils.getSysUser().getAuthorities()).build());
    }

    @GetMapping("/table")
    @Operation(summary = "sys-menu-table", description = "獲取菜單頁面的表")
    public ServerResponseEntity<List<SysMenu>> table() {
        List<SysMenu> sysMenuList = sysMenuService.listMenuAndBtn();
        return ServerResponseEntity.success(sysMenuList);
    }

    @GetMapping("/list")
    @Operation(summary = "sys-menu-list", description = "通過userId獲取用戶擁有的菜單和權限 (不包含按鈕)")
    public ServerResponseEntity<List<SysMenu>> list() {
        List<SysMenu> menuList = sysMenuService.listSimpleMenuNoButton();
        return ServerResponseEntity.success(menuList);
    }

    @GetMapping("/listRootMenu")
    @Operation(summary = "list-root-menu", description = "選擇菜單")
    public ServerResponseEntity<List<SysMenu>> listRootMenu() {
        // 查詢列表數據
        List<SysMenu> menuList = sysMenuService.listRootMenu();
        return ServerResponseEntity.success(menuList);
    }

    @GetMapping("/listChildrenMenu")
    @Operation(summary = "children-menu", description = "選擇子菜單")
    public ServerResponseEntity<List<SysMenu>> listChildrenMenu(Long parentId) {
        // 查詢列表數據
        List<SysMenu> menuList = sysMenuService.listChildrenMenuByParentId(parentId);
        return ServerResponseEntity.success(menuList);
    }

    @GetMapping("/info/{menuId}")
    @PreAuthorize("@pms.hasPermission('sys:menu:info')")
    @Operation(summary = "sys-menu-info", description = "菜單訊息")
    public ServerResponseEntity<SysMenu> info(@PathVariable("menuId") Long menuId) {
        return ServerResponseEntity.success(sysMenuService.getById(menuId));
    }

    @PostMapping("/save")
    @SysOperationLog("保存菜單")
    @PreAuthorize("@pms.hasPermission('sys:menu:save')")
    @Operation(summary = "sys-menu-save", description = "保存菜單訊息")
    public ServerResponseEntity save(@RequestBody @Valid SysMenu sysMenu) {
        // 數據校驗
        verifyForm(sysMenu);
        sysMenuService.save(sysMenu);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("修改菜單")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('sys:menu:update')")
    @Operation(summary = "sys-menu-update", description = "修改菜單")
    public ServerResponseEntity<String> update(@RequestBody @Valid SysMenu sysMenu) {
        // 數據校驗
        verifyForm(sysMenu);
        if (sysMenu.getType() == MenuType.MENU.getValue()) {
            if (StrUtil.isBlank(sysMenu.getUrl())) {
                return ServerResponseEntity.showFailMsg("菜單url不能為空");
            }
        }
        sysMenuService.updateById(sysMenu);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/{menuId}")
    @SysOperationLog("刪除菜單")
    @PreAuthorize("@pms.hasPermission('sys:menu:delete')")
    @Operation(summary = "sys-menu-delete", description = "刪除菜單")
    public ServerResponseEntity<String> delete(@PathVariable Long menuId) {
        if (menuId <= Constant.SYS_MENU_MAX_ID) {
            return ServerResponseEntity.showFailMsg("系統菜單，不能刪除");
        }
        // 判斷是否有子菜單或按鈕
        List<SysMenu> menuList = sysMenuService.listChildrenMenuByParentId(menuId);
        if (!menuList.isEmpty()) {
            return ServerResponseEntity.showFailMsg("請先刪除子菜單或按鈕");
        }
        sysMenuService.deleteMenuAndRoleMenu(menuId);
        return ServerResponseEntity.success();
    }

    private void verifyForm(SysMenu sysMenu) {
        if (sysMenu.getType() == MenuType.MENU.getValue()) {
            throw new ShopException("菜單url不能為空");
        }

        if (Objects.equals(sysMenu.getMenuId(), sysMenu.getParentId())) {
            throw new ShopException("不能修改自己的上級");
        }
        // 上級菜單類型
        int parentType = MenuType.CATALOG.getValue();
        if (sysMenu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.getById(sysMenu.getParentId());
            parentType = parentMenu.getType();
        }

        // 目錄、菜單
        if (sysMenu.getType() == MenuType.CATALOG.getValue() || sysMenu.getType() == MenuType.MENU.getValue()) {
            if (parentType != MenuType.CATALOG.getValue()) {
                throw new ShopException("上級菜單只能為目錄類型");
            }
            return;
        }
        // 按鈕
        if (sysMenu.getType() == MenuType.BUTTON.getValue()) {
            if (parentType != MenuType.MENU.getValue()) {
                throw new ShopException("上級菜單只能為菜單類型");
            }
        }

    }

}
