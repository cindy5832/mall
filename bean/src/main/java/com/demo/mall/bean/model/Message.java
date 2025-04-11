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

/**
 * @author lanhai
 */
@Data
@TableName("tz_message")
@Schema(description = "留言")
public class Message implements Serializable {

    @TableId
    @Schema(description = "留言id")
    private Long id;

    @Schema(description = "留言創建時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "姓名")
    private String userName;

    @Schema(description = "mail")
    private String email;

    @Schema(description = "聯絡方式")
    private String contact;

    @Schema(description = "狀態 0 未審核 1 審核通過")
    private Integer status;

    @Schema(description = "留言內容")
    private String content;

    @Schema(description = "留言回復")
    private String reply;

}
