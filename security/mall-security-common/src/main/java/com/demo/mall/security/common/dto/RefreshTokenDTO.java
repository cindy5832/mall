
package com.demo.mall.security.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 刷新token
@Data
public class RefreshTokenDTO {

    @NotBlank(message = "refreshToken不能為空")
    @Schema(description = "refreshToken")
    private String refreshToken;


    @Override
    public String toString() {
        return "RefreshTokenDTO{" + "refreshToken='" + refreshToken + '\'' + '}';
    }

}
