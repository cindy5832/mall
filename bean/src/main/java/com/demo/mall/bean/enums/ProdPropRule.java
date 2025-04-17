/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.enums;

// 商品規格參數、屬性類型
public enum ProdPropRule {

    // 規格屬性 (用於商品發布，關聯sku)
    SPEC(1),

    // 規格參數 (用於商品搜尋，與分類搜尋關聯)
    ATTRIBUTE(2);

    private Integer num;

    public Integer value() {
        return num;
    }

    ProdPropRule(Integer num) {
        this.num = num;
    }

    public static ProdPropRule instance(Integer value) {
        ProdPropRule[] enums = values();
        for (ProdPropRule statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
