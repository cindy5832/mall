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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.mall.bean.vo.UserVO;
import com.demo.mall.common.serializer.json.ImgJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
@TableName("tz_prod_comm")
@Schema(description = "商品評論")
@EqualsAndHashCode
public class ProdComm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @Schema(description = "商品評論id")
    private Long prodCommId;

    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "訂單項目id")
    private Long orderItemId;

    @Schema(description = "評論用戶id")
    private String userId;

    @Schema(description = "評論內容")
    private String content;

    @Schema(description = "回覆內容")
    private String replyContent;

    @Schema(description = "記錄時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recTime;

    @Schema(description = "回復時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    @Schema(description = "是否回復 0 未回覆 1 以回復")
    private Integer replySts;

    @Schema(description = "ip來源")
    private String postip;

    @Schema(description = "分數 0-5分")
    private Integer score;

    @Schema(description = "有用的計數")
    private Integer usefulCounts;

    @Schema(description = "附圖的json 以 , 隔開")
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pics;

    @Schema(description = "是否匿名 0 否 1 是")
    private Integer isAnonymous;

    @Schema(description = "是否顯示 1 顯示 0 待審核 -1 未通過審核，不顯示；若需要審核評論 0 是 1 否")
    private Integer status;

    @Schema(description = "評價 0 好評 1 中評 2 負評")
    private Integer evaluate;

    @Schema(description = "關聯用戶")
    @TableField(exist = false)
    private UserVO user;

    @Schema(description = "商品名稱")
    @TableField(exist = false)
    private String prodName;
}
