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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_hot_search")
@Schema(description = "熱搜")
public class HotSearch implements Serializable {

    @TableId
    private Long hotSearchId;

    @Schema(description = "商店id 0為全球熱搜")
    private Long shopId;

    @Schema(description = "熱搜標題")
    private String title;

    @Schema(description = "內容")
    private String content;

    @Schema(description = "創建時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recDate;

    @Schema(description = "順序")
    private Integer seq;

    @Schema(description = "狀態 默認 1開啟 0 關閉")
    private Integer status;

}