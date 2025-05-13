package com.demo.mall.security.common.controller;

import cn.hutool.core.bean.BeanUtil;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.common.bo.TokenInfoBO;
import com.demo.mall.security.common.dto.RefreshTokenDTO;
import com.demo.mall.security.common.manager.TokenStore;
import com.demo.mall.security.common.vo.TokenInfoVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "token")
public class TokenController {

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/token/refresh")
    public ServerResponseEntity<TokenInfoVO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        TokenInfoBO tokenInfoBO = tokenStore.refreshToken(refreshTokenDTO.getRefreshToken());
        return ServerResponseEntity.success(BeanUtil.copyProperties(tokenInfoBO, TokenInfoVO.class));
    }
}
