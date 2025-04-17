/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.security.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用於登入傳遞帳密")
public class AuthenticationDTO {

    @NotBlank(message = "userName不能为空")
    @Schema(description = "用戶名/email/手機號碼", requiredMode = Schema.RequiredMode.REQUIRED)
    protected String userName;

    @NotBlank(message = "passWord不能为空")
    @Schema(description = "一般用密碼", requiredMode = Schema.RequiredMode.REQUIRED)
    protected String passWord;

}
