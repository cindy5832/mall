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

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tz_attach_file")
@Schema(description = "附加文件")
public class AttachFile implements Serializable {
    @TableId
    @Schema(description = "文件id")
    private Long fileId;

    @Schema(description = "文件路徑")
    private String filePath;

    @Schema(description = "文件類型")
    private String fileType;

    @Schema(description = "文件大小")
    private Integer fileSize;

    @Schema(description = "上傳時間")
    private Date uploadTime;

    @Schema(description = "文件關聯表主鍵id")
    private Long fileJoinId;

    @Schema(description = "文件關聯表類型：1 商品表 @see FileJoinType")
    private Integer fileJoinType;
}