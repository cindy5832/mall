package com.demo.mall.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.system.model.SysRole;
import com.demo.mall.system.service.SysMenuService;
import com.demo.mall.system.service.SysRoleService;
import com.demo.mall.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
@Tag(name = "sys-role", description = "角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys:role:page')")
    @Operation(summary = "sys-role-page", description = "角色列表")
    public ServerResponseEntity<IPage<SysRole>> page(String roleName, PageParam<SysRole> page) {
        IPage<SysRole> sysRoleIPage = sysRoleService.page(page, new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(roleName), SysRole::getRoleName, roleName));
        return ServerResponseEntity.success(sysRoleIPage);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @Operation(summary = "sys-role-list", description = "角色列表")
    public ServerResponseEntity<List<SysRole>> list() {
        return ServerResponseEntity.success(sysRoleService.list());
    }

    @GetMapping("/info/{roleId}")
    @PreAuthorize("@pms.hasPermission('sys:role:info')")
    @Operation(summary = "sys-role-info", description = "角色訊息")
    public ServerResponseEntity<SysRole> info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        // 查詢角色對應的菜單
        List<Long> menuList = sysMenuService.listMenuByRoleId(roleId);
        role.setMenuIdList(menuList);
        return ServerResponseEntity.success(role);
    }

    @SysOperationLog("保存角色")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('sys:role:save')")
    @Operation(summary = "sys-role-info-save", description = "保存角色")
    public ServerResponseEntity save(@RequestBody SysRole role) {
        sysRoleService.saveRoleAndRoleMenu(role);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("修改角色")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('sys:role:update')")
    @Operation(summary = "sys-role-update", description = "修改角色")
    public ServerResponseEntity update(@RequestBody SysRole role) {
        sysRoleService.updateRoleAndRoleMenu(role);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("刪除角色")
    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('sys:role:delete')")
    @Operation(summary = "delete-role", description = "刪除角色")
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        sysRoleService.deleteBatch(ids);
        return ServerResponseEntity.success();
    }
}
