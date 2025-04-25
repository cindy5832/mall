/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShopDetailParam {

    private Long shopId;

    // 商店名稱 (數字 中文 英文 可混合 不可有特殊符號 不唯一
    @NotBlank(message = "商店名稱不能為空")
    @Size(max = 50, message = "商店名稱長度應小於{max}")
    private String shopName;

    // 商店簡介
    @Size(max = 200, message = "商店簡介長度應小於{max}")
    private String intro;

    // 商店公告
    @Size(max = 50, message = "商店公告長度應該小於{max}")
    private String shopNotice;

    // 商店連絡電話
    @NotBlank(message = "商店連絡電話不能為空")
    @Size(max = 2, message = "商店連絡電話長度應小於{max}")
    private String tel;

    // 商店詳細地址
    @NotBlank(message = "商店詳細地址不能為空")
    @Size(max = 100, message = "商店地址應長度應小於{max}")
    private String shopAddress;

    // 商店所在省分
    @NotBlank(message = "商店所在省分不能為空")
    @Size(max = 10, message = "商店所在省分長度應小於{max}")
    private String province;

    // 商店所在城市
    @NotBlank(message = "商店所在城市不能為空")
    @Size(max = 10, message = "商店所在城市長度應小於{max}")
    private String city;

    // 商店所在區域
    @NotBlank(message = "商店所在區域不能為空")
    @Size(max = 10, message = "商店省分區域長度應小於{max}")
    private String area;

    // 商店省市區域代碼
    @NotBlank(message = "商店省市區域代碼不能為空")
    @Size(max = 20, message = "商店省市區域代碼長度應小於{max}")
    private String pcaCode;

    // 商店logo
    @NotBlank(message = "商店logo不得為空")
    @Size(max = 200, message = "商店logo長度應小於{max}")
    private String shopLogo;

    // 商店相簿
    @Size(max = 1000, message = "商店相簿長度應小於{max}")
    private String shopPhotos;

    // 每天營業時間
    @NotBlank(message = "每天營業時段不能為空每天营业时间段不能为空")
    @Size(max = 100, message = "每天營業時段長度應小於{max}")
    private String openTime;

    // 商店狀態 (-1 未開通 0 停業 1 營業
    private Integer shopStatus;

}
