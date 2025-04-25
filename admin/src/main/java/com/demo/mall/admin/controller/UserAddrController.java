package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.UserAddr;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.UserAddrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/addr")
@Tag(name = "user-addr", description = "用戶地址管理")
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;

    @GetMapping("/page")
    @Operation(summary = "user-addr-page", description = "用戶地址分頁查詢")
    public ServerResponseEntity<IPage<UserAddr>> getUserAddrPage(PageParam<UserAddr> page, UserAddr userAddr) {
        return ServerResponseEntity.success(userAddrService.page(page, new LambdaQueryWrapper<UserAddr>()));
    }

    @GetMapping("/info/{addrId}")
    @Operation(summary = "user-addr-info", description = "根據id查詢用戶地址")
    public ServerResponseEntity<UserAddr> getUserAddr(@PathVariable("addrId") Long addrId) {
        return ServerResponseEntity.success(userAddrService.getById(addrId));
    }

    @SysOperationLog("修改用戶地址管理")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('user:addr:update')")
    public ServerResponseEntity updateUserAddr(@RequestBody @Valid UserAddr userAddr) {
        return ServerResponseEntity.success(userAddrService.updateById(userAddr));
    }

    @SysOperationLog("刪除用戶地址管理")
    @DeleteMapping("/delete/{addrId}")
    @PreAuthorize("@pms.hasPermission('user:addr:delete')")
    public ServerResponseEntity deleteUserAddr(@PathVariable("addrId") Long addrId) {
        return ServerResponseEntity.success(userAddrService.removeById(addrId));
    }
}
