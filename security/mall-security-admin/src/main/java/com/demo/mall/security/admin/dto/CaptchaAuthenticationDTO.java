package com.demo.mall.security.admin.dto;

import com.demo.mall.security.common.dto.AuthenticationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "驗證碼登入")
public class CaptchaAuthenticationDTO extends AuthenticationDTO {

    @Schema(description = "驗證碼", requiredMode = Schema.RequiredMode.REQUIRED)
    private String captchaVerification;
}
