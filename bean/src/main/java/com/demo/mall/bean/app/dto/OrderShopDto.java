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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Schema(description = "訂單下每個商店")
@Data
public class OrderShopDto implements Serializable {

    @Schema(description = "商店id")
    private Long shopId;

    @Schema(description = "商店名稱")
    private String shopName;

    @Schema(description = "實際總額")
    private Double actualTotal;

    @Schema(description = "商品總值")
    private Double total;

    @Schema(description = "商品總數")
    private Integer totalNum;

    @Schema(description = "地址Dto")
    private UserAddrDto userAddrDto;

    @Schema(description = "商品訊息")
    private List<OrderItemDto> orderItemDtos;

    @Schema(description = "運費")
    private Double transfee;

    @Schema(description = "優惠總額")
    private Double reduceAmount;

    @Schema(description = "促銷活動優惠金額")
    private Double discountMoney;

    @Schema(description = "優惠券優惠金額")
    private Double couponMoney;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "訂單創建時間")
    private Date createTime;

    @Schema(description = "訂單備註訊息")
    private String remarks;

    @Schema(description = "訂單狀態")
    private Integer status;
}
