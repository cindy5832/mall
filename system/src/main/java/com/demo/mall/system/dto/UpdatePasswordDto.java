/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "更新密碼參數")
public class UpdatePasswordDto {

    @NotBlank(message = "舊密碼不能為空")
    @Size(max = 50)
    @Schema(description = "舊密碼")
    private String password;

    @NotBlank(message = "新密碼不能為空")
    @Size(max = 50)
    @Schema(description = "新密碼")
    private String newPassword;
}
