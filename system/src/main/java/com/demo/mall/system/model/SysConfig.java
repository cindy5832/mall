/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.system.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "系統配置訊息")
@Data
@TableName("tz_sys_config")
public class SysConfig {

    @TableId
    private Long id;

    @Schema(description = "key")
    @NotBlank(message = "參數名稱不能為空")
    private String paramKey;

    @Schema(description = "value")
    @NotBlank(message = "參數值不能為空")
    private String paramValue;

    @Schema(description = "備註")
    private String remark;

}
