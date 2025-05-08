/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger文檔，只有在測試環境才會使用
@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi createRestApi() {
        return GroupedOpenApi.builder()
                .group("api文檔")
                .packagesToScan("com.yami.shop.api").build();
    }


    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Mall4j api 文檔")
                        .description("Mall4j接口文檔，openapi3.0 接口，用於前端對接")
                        .version("v0.0.1")
                        .license(new License().name("使用请遵守AGPL3.0授權協議").url("https://www.mall4j.com")));
    }
}
