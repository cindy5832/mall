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

import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.Sku;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author lanhai
 */
@Data
public class ProductParam {

    // 商品ID
    private Long prodId;

    // 狀態
    private Integer status;

    // 商品名稱
    @NotBlank(message = "商品名稱不能為空")
    @Size(max = 200, message = "商品長度應小於{max}")
    private String prodName;


    /// 商品價格
    @NotNull(message = "商品售價不能為空")
    private Double price;

    // 商品原價
    @NotNull(message = "商品原價不能為空")
    private Double oriPrice;

    // 庫存量
    @NotNull(message = "商品庫存量不能為空")
    private Integer totalStocks;

    // 簡要描述，賣點等
    @Size(max = 500, message = "商品概要長度應小於{max}")
    private String brief;

    @NotBlank(message = "請選擇上傳圖片")
    private String pic;

    // 商品圖片
    @NotBlank(message = "請選擇上傳圖片")
    private String imgs;

    // 商品分類
    @NotNull(message = "請選擇商品分類")
    private Long categoryId;

    // SKU列表
    private List<Sku> skuList;

    // 商品詳情
    private String content;

    // 用戶是否能自取
    private Product.DeliveryModeVO deliveryModeVo;

    // 運費模板id
    private Long deliveryTemplateId;

    // 分組標籤列表
    private List<Long> tagList;

}
