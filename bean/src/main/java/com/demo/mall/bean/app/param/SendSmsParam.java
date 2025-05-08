/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "發送驗證碼參數")
@Data
public class SendSmsParam {

    @Schema(description = "手機號")
    @Pattern(regexp = "1[0-9]{10}", message = "請輸入正確的手機號")
    private String mobile;

}
