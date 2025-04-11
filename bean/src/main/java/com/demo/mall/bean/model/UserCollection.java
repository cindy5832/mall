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
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_user_collection")
@Schema(description = "用戶收藏表")
@EqualsAndHashCode
public class UserCollection implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @Schema(description = "收藏表id")
    private Long id;

    @Schema(description = "商品id")
    private Long prodId;

    @Schema(description = "用戶id")
    private String userId;

    @Schema(description = "收藏時間")
    private Date createTime;

}
