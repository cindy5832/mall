/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "購物車參數")
@Data
public class ShopCartParam {

    @Schema(description = "購物項id")
    private Long basketId;

    @Schema(description = "活動id，傳0則不參與該活動")
    private Long discountId;
}
