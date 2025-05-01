/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger文檔，僅在測試環境使用
@Configuration
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi baseRestApi() {
		return GroupedOpenApi.builder()
				.group("Api文檔")
				.packagesToScan("com.demo").build();
	}


	@Bean
	public OpenAPI springShopOpenApi() {
		return new OpenAPI()
				.info(new Info().title("Mall_Api 文檔")
						.description("Mall_Api 文檔")
						.version("v0.0.1")
						.license(new License().name("使用請遵守AGPL3.0授權協議").url("https://www.mall4j.com")));
	}
}
