package com.demo.mall.security.common.config;

import cn.hutool.core.util.ArrayUtil;
import com.demo.mall.security.common.adapter.AuthConfigAdapter;
import com.demo.mall.security.common.adapter.DefaultAuthConfigAdapter;
import com.demo.mall.security.common.filter.AuthFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Tag(name = "auth", description = "授權配置")
@EnableMethodSecurity
@Configuration
public class AuthConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @ConditionalOnMissingBean
    public AuthConfigAdapter authConfigAdapter() {
        return new DefaultAuthConfigAdapter();
    }

    @Bean
    @Lazy
    public FilterRegistrationBean<AuthFilter> authFilter(AuthConfigAdapter authConfigAdapter) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        // 添加過濾器
        registration.setFilter(authFilter);
        // 設置過濾路徑， /*所有路徑
        registration.addUrlPatterns(ArrayUtil.toArray(authConfigAdapter.pathPatterns(), String.class));
        registration.setName("authFilter");
        // 設置優先級
        registration.setOrder(0);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }
}
