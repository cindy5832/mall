/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.security.common.filter;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.handler.HttpHandler;
import com.demo.mall.common.response.ResponseEnum;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.common.adapter.AuthConfigAdapter;
import com.demo.mall.security.common.bo.UserInfoInTokenBO;
import com.demo.mall.security.common.manager.TokenStore;
import com.demo.mall.security.common.util.AuthUserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;

// 授權過濾，要實現AuthConfigAdapter街口，添加對應路徑即可
@Component
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthConfigAdapter authConfigAdapter;

    @Autowired
    private HttpHandler httpHandler;

    @Autowired
    private TokenStore tokenStore;

    @Value("${sa-token.token-name}")
    private String tokenName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestUri = req.getRequestURI();

        List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();

        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 如果匹配不需要授權的路徑，就不需要校驗是否需要授權
        if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
            for (String excludePathPattern : excludePathPatterns) {
                if (pathMatcher.match(excludePathPattern, requestUri)) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }

        String accessToken = req.getHeader(tokenName);
        // 也許需要登入，不登入也能用的uri
        boolean mayAuth = pathMatcher.match(AuthConfigAdapter.MAYBE_AUTH_URI, requestUri);


        UserInfoInTokenBO userInfoInToken = null;

        try {
            // 如果有token，就要獲取token
            if (StrUtil.isNotBlank(accessToken)) {
                // 校驗登入，並從緩存中取出用戶訊息
                try {
                    StpUtil.checkLogin();
                } catch (Exception e) {
                    httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
                    return;
                }
                userInfoInToken = tokenStore.getUserInfoByAccessToken(accessToken, true);
            }
            else if (!mayAuth) {
                // 返回前端401
                httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
                return;
            }
            // 保存上下文
            AuthUserContext.set(userInfoInToken);

            chain.doFilter(req, resp);

        }catch (Exception e) {
            // 手动捕获下非controller异常
            if (e instanceof ShopException){
                httpHandler.printServerResponseToWeb((ShopException) e);
            } else {
                throw e;
            }
        } finally {
            AuthUserContext.clean();
        }
    }
}
