package com.demo.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.User;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "admin-user", description = "管理員")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:user:page')")
    @Operation(summary = "admin-user-page", description = "分頁獲取管理員")
    public ServerResponseEntity<IPage<User>> page(User user, PageParam<User> page) {
        IPage<User> userIPage = userService.page(page, new LambdaQueryWrapper<User>()
                .like(StrUtil.isNotBlank(user.getNickName()), User::getNickName, user.getNickName())
                .eq(user.getStatus() != null, User::getStatus, user.getStatus()));
        for (User user1 : userIPage.getRecords()) {
            user1.setNickName(StrUtil.isBlank(user1.getNickName()) ? "" : user1.getNickName());
        }
        return ServerResponseEntity.success(userIPage);
    }

    @GetMapping("/info/{userId}")
    @PreAuthorize("@pms.hasPermission('admin:user:info')")
    @Operation(summary = "admin-user-info", description = "根據UserId獲取管理員訊息")
    public ServerResponseEntity<User> info(@PathVariable("userId") String userId) {
        User user = userService.getById(userId);
        user.setNickName(StrUtil.isBlank(user.getNickName()) ? "" : user.getNickName());
        return ServerResponseEntity.success(user);
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:user:update')")
    @Operation(summary = "admin-user-update", description = "修改管理員資訊")
    public ServerResponseEntity update(@RequestBody User user) {
        user.setModifyTime(new Date());
        user.setNickName(StrUtil.isBlank(user.getNickName()) ? "" : user.getNickName());
        userService.updateById(user);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('admin:user:delete')")
    @Operation(summary = "admin-user-delete", description = "刪除管理員")
    public ServerResponseEntity delete(@RequestBody String[] userIds) {
        userService.removeBatchByIds(Arrays.asList(userIds));
        return ServerResponseEntity.success();
    }
}
