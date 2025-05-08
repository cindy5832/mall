package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.demo.mall.bean.app.dto.UserDto;
import com.demo.mall.bean.app.param.UserInfoParam;
import com.demo.mall.bean.model.User;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/p/user")
@Tag(name = "user", description = "用戶api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @Operation(summary = "info", description = "根據userId查看用戶訊息")
    public ServerResponseEntity<UserDto> userInfo() {
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getById(userId);
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        return ServerResponseEntity.success(userDto);
    }

    @PutMapping("/setUserInfo")
    @Operation(summary = "set-info", description = "設置用戶訊息")
    public ServerResponseEntity setUserInfo(@RequestBody UserInfoParam userInfoParam) {
        String userId = SecurityUtils.getUser().getUserId();
        User user = new User();
        user.setUserId(userId);
        user.setPic(userInfoParam.getAvatarUrl());
        user.setNickName(userInfoParam.getNickName());
        userService.updateById(user);
        return ServerResponseEntity.success();
    }
}
