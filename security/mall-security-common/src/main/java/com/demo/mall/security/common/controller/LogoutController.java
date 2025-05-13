package com.demo.mall.security.common.controller;

import cn.hutool.core.util.StrUtil;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.common.manager.TokenStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "logout", description = "登出")
public class LogoutController {
    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/logout")
    @Operation(summary = "logout", description = "退出登入，清楚token、清除菜单缓存")
    public ServerResponseEntity logout (HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StrUtil.isBlank(token)) {
            return ServerResponseEntity.success();
        }
        tokenStore.deleteCurrentToken(token);
        return ServerResponseEntity.success();
    }
}
