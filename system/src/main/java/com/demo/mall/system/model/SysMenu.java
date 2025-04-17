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


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "系統menu管理")
@Data
@TableName("tz_sys_menu")
public class SysMenu implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Menu id")
    @TableId
    private Long menuId;

    @Schema(description = "父menu id 一級menu 為0")
    @NotNull(message = "上级菜单不能为空")
    private Long parentId;

    @Schema(description = "父菜單名稱")
    @TableField(exist = false)
    private String parentName;

    @Schema(description = "菜單名稱")
    @NotBlank(message = "Menu 名稱不能為空")
    private String name;

    @Schema(description = "menu url")
    private String url;

    @Schema(description = "權限 (以 , 分隔，如：user:list,user:create)")
    private String perms;

    @Schema(description = "類型 0 目錄 1 menu 2 按鈕")
    private Integer type;

    @Schema(description = "menu icon")
    private String icon;

    @Schema(description = "排序")
    private Integer orderNum;

    @TableField(exist = false)
    private List<?> list;

}
