/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("tz_transcity_free")
public class TranscityFree implements Serializable {
    @Serial
    private static final long serialVersionUID = 2579465286635831076L;

    @TableId
    @Schema(description = "指定條件郵寄城市id")
    private Long transcityFreeId;

    @Schema(description = "指定條件郵件id")
    private Long transfeeFreeId;

    @Schema(description = "城市id")
    private Long freeCityId;
}
