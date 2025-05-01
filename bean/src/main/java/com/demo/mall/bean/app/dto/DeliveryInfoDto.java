/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeliveryInfoDto {

    @Schema(description = "詳細訊息")
    private String context;

    private String ftime;

    @Schema(description = "快遞區域")
    private String location;

    @Schema(description = "物流更新時間")
    private String time;

}
