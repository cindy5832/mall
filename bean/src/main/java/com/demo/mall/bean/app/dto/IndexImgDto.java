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

import com.demo.mall.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "首頁輪播圖")
@Data
public class IndexImgDto {

    @JsonSerialize(using = ImgJsonSerializer.class)
    @Schema(description = "圖片Url")
    private String imgUrl;

    @Schema(description = "圖片順序")
    private Integer seq;

    @Schema(description = "上傳時間")
    private Date uploadTime;

    @Schema(description = "類型")
    private int type;

    @Schema(description = "關聯id")
    private Long relation;


}
