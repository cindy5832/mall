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
import java.util.Date;

@Data
@TableName("tz_shop_detail")
@Schema(description = "商店")
public class ShopDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 3300529542917772262L;

    @TableId
    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "商店名稱(數位、中文，英文(可混合，不可有特殊字元)，可修改)、不唯一")
    private String shopName;

    @Schema(description = "店長用戶id")
    private String userId;

    @Schema(description = "商店類型")
    private Integer shopType;

    @Schema(description = "商店簡介(可修改)")
    private String intro;

    @Schema(description = "商店公告(可修改)")
    private String shopNotice;

    @Schema(description = "商店行業(餐飲、生鮮果蔬、鮮花等)")
    private Integer shopIndustry;

    @Schema(description = "店長")
    private String shopOwner;

    @Schema(description = "商店綁定的手機(登錄帳號：唯一)")
    private String mobile;

    @Schema(description = "商店聯繫電話")
    private String tel;

    @Schema(description = "商店所在緯度(可修改)")
    private String shopLat;

    @Schema(description = "商店所在經度(可修改)")
    private String shopLng;

    @Schema(description = "商店詳細地址")
    private String shopAddress;

    @Schema(description = "商店所在省份（描述）")
    private String province;

    @Schema(description = "商店所在城市（描述）")
    private String city;

    @Schema(description = "商店所在區域（描述）")
    private String area;

    @Schema(description = "商店省市區代碼，用於回顯")
    private String pcaCode;

    @Schema(description = "商店logo(可修改)")
    private String shopLogo;

    @Schema(description = "商店相冊")
    private String shopPhotos;

    @Schema(description = "每天營業時間段(可修改)")
    private String openTime;

    @Schema(description = "商店狀態(-1:未開通 0: 停業中 1:營業中)，可修改")
    private Integer shopStatus;

    @Schema(description = "0:商家承擔運費; 1:買家承擔運費")
    private Integer transportType;

    @Schema(description = "固定運費")
    private Double fixedFreight;

    @Schema(description = "滿X包郵")
    private Double fullFreeShipping;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "更新時間")
    private Date updateTime;

    @Schema(description = "分銷開關(0:開啟 1:關閉)")
    private Integer isDistribution;
}
