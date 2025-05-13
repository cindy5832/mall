package com.demo.mall.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.security.common.enums.SysTypeEnum;
import com.demo.mall.security.common.manager.PasswordManager;
import com.demo.mall.security.common.manager.TokenStore;
import com.demo.mall.system.constant.Constant;
import com.demo.mall.system.dto.UpdatePasswordDto;
import com.demo.mall.system.model.SysUser;
import com.demo.mall.system.service.SysRoleService;
import com.demo.mall.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sys/user")
@Tag(name = "system-user", description = "系統用戶")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys:user:page')")
    @Operation(summary = "sys-user-page", description = "所有用戶列表")
    public ServerResponseEntity<IPage<SysUser>> page(PageParam<SysUser> page, String userName) {
        IPage<SysUser> sysUserIPage = sysUserService.page(page, new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getShopId, SecurityUtils.getSysUser().getShopId())
                .like(StrUtil.isNotBlank(userName), SysUser::getUsername, userName));
        return ServerResponseEntity.success(sysUserIPage);
    }

    @GetMapping("/info")
    @Operation(summary = "sys-user-info", description = "獲取登入用戶訊息")
    public ServerResponseEntity<SysUser> info() {
        return ServerResponseEntity.success(sysUserService.getSysUserById(SecurityUtils.getSysUser().getUserId()));
    }

    @SysOperationLog("修改密碼")
    @PostMapping("/password")
    @Operation(summary = "sys-user-password", description = "修改當前登入的用戶密碼")
    public ServerResponseEntity<String> password(@Valid @RequestBody UpdatePasswordDto param) {
        Long userId = SecurityUtils.getSysUser().getUserId();
        // 開源代碼，禁止用戶修改admin的帳號密碼
        // 正式使用時，刪除此部分代碼即可
        if (Objects.equals(1L, userId) && StrUtil.isNotBlank(param.getNewPassword())) {
            throw new ShopException("禁止修改admin的帳號密碼");
        }
        SysUser dbUser = sysUserService.getSysUserById(userId);
        String password = passwordManager.decryptPassword(param.getPassword());
        if(!passwordEncoder.matches(password, dbUser.getPassword())) {
            return ServerResponseEntity.showFailMsg("原密碼不正確");
        }
        // 新密碼
        String newPassword = passwordEncoder.encode(passwordManager.decryptPassword(param.getNewPassword()));
        // 更新密碼
        sysUserService.updatePasswordByUserId(userId, newPassword);
        tokenStore.deleteAllToken(String.valueOf(SysTypeEnum.ADMIN.value()), String.valueOf(userId));
        return ServerResponseEntity.success();
    }

    @GetMapping("/info/{userId}")
    @PreAuthorize("@pms.hasPermission('sys:user:info')")
    @Operation(summary = "sys-user-info", description = "用戶訊息")
    public ServerResponseEntity<SysUser> info(@PathVariable("userId") Long userId) {
        SysUser sysUser = sysUserService.getSysUserById(userId);
        if(!Objects.equals(sysUser.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            throw new ShopException("沒有權限獲得該用戶訊息");
        }
        // 獲取用戶所屬的角色列表
        List<Long> roleIdList = sysRoleService.listRoleIdByUserId(userId);
        sysUser.setRoleIdList(roleIdList);
        return ServerResponseEntity.success(sysUser);
    }

    @SysOperationLog("保存用戶")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('sys:user:save')")
    @Operation(summary = "sys-user-save", description = "保存用戶")
    public ServerResponseEntity<String> save(@Valid @RequestBody SysUser sysUser) {
        String userName = sysUser.getUsername();
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, userName));
        if (user != null){
            return ServerResponseEntity.showFailMsg("該用戶已存在");
        }
        sysUser.setShopId(SecurityUtils.getSysUser().getShopId());
        sysUser.setPassword(passwordEncoder.encode(passwordManager.decryptPassword(sysUser.getPassword())));
        sysUserService.saveUserAndUserRole(sysUser);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("修改用戶")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('sys:user:update')")
    @Operation(summary = "sys-user-update", description = "修改用戶")
    public ServerResponseEntity<String> update(@Valid @RequestBody SysUser user) {
        String password = passwordManager.decryptPassword(user.getPassword());
        SysUser dbUser =sysUserService.getSysUserById(user.getUserId());
        if(!Objects.equals(dbUser.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            throw new ShopException("沒有權限修改該用戶訊息");
        }
        SysUser dbUserNameInfo = sysUserService.getByUserName(dbUser.getUsername());
        if (dbUserNameInfo != null && !Objects.equals(dbUserNameInfo.getUserId(), user.getUserId())) {
            return ServerResponseEntity.showFailMsg("該用戶已存在");
        }
        if(StrUtil.isBlank(password)) {
            user.setPassword(null);
        }else {
            user.setPassword(passwordEncoder.encode(password));
        }
        // 開原版 - 禁止用戶修改admin的帳號密
        // 正式使用時，刪除此部分代碼
        boolean is = Objects.equals(1L, dbUser.getUserId()) && (StrUtil.isNotBlank(password)) || !StrUtil.equals("admin", user.getUsername());
        if(is){
            throw new ShopException("禁止修改admin的帳號密碼");
        }
        if(Objects.equals(1L, user.getUserId()) && user.getStatus() == 0){
            throw new ShopException("admin用戶不可被禁用");
        }
        sysUserService.updateUserAndUserRole(user);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("刪除用戶")
    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('sys:user:delete')")
    @Operation(summary = "delete-sysUse", description = "刪除用戶")
    public ServerResponseEntity<String> delete(@Valid @RequestBody Long[] userIds) {
        if(userIds.length == 0){
            return ServerResponseEntity.showFailMsg("請選擇需要刪除的用戶");
        }
        if (ArrayUtil.contains(userIds, Constant.SUPER_ADMIN_ID)){
            return ServerResponseEntity.showFailMsg("系統管理員不能刪除");
        }
        if(ArrayUtil.contains(userIds, SecurityUtils.getSysUser().getUserId())){
            return ServerResponseEntity.showFailMsg("當前用戶不能刪除");
        }
        sysUserService.deleteBatch(userIds, SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }
}
