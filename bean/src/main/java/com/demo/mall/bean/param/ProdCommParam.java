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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "添加評論訊息")
public class ProdCommParam {

    @Schema(description = "商品id" )
    private Long prodId;

    @Schema(description = "訂單項ID" )
    private Long orderItemId;

    @Schema(description = "評價，0-5分")
    @NotNull(message = "評價不能為空")
    private Integer score;

    @Schema(description = "評論內容")
    private String content;

    @Schema(description = "評論圖片，用逗號分隔" )
    private String pics;

    @Schema(description = "是否匿名 (1:是  0:否) 默認為否" )
    private Integer isAnonymous;


    @Schema(description = "* 評價(0好評 1中評 2差評)" )
    private Integer evaluate;

}
