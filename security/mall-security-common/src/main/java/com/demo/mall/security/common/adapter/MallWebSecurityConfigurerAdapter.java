package com.demo.mall.security.common.adapter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

/**
 * 使用security 防火牆功能 但不使用security 認證授權登入
 **/
@Component
@EnableWebSecurity
public class MallWebSecurityConfigurerAdapter {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> {})
                .sessionManagement(httpSecuritySessionManagement -> {})
                .authorizeHttpRequests(
                        auth->{
                            auth.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                    .anyRequest().permitAll();
                        }
                );
        return http.build();
    }

}
