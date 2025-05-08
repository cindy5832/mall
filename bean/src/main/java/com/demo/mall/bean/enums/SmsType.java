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

public enum SmsType {


    // 發送驗證碼
    VALID(0, "SMS_152288010", "感謝您對xxx的支持。您的驗證碼是${code}，請勿將驗證碼洩漏給第三方。"),

    // 商品庫存不足通知
    STOCKS_ARM(1, "SMS_152288054", "尊敬的${name}，感謝您對xxx的支持。您的${prodName}庫存僅剩${num}，為避免影響您的客戶下單，請及時補貨！"),

    // 行業客戶審核通過通知
    DISTRIBUTOR_PASS_AUDIT(2, "SMS_152283207", "尊敬的${name}，感謝您對xxx的支持。您提交的資料已審核通過！祝您購物愉快!"),

    // 分銷商訂單購買成功通知
    DISTRIBUTOR_BUY_SUCCESS(3, "SMS_152283148", "尊敬的${name}，感谢您對xxx的支持。您的訂單${orderNumber}已確認成功，我們會盡速發貨！"),

    // 用戶發貨通知
    NOTIFY_DVY(4, "SMS_152283152", "尊敬的${name}，感謝您對xxx的支持。您的訂單${orderNumber}已通過${dvyName}發貨，快遞單號是：${dvyFlowId}。請注意查收。"),

    // 代理商審核通知
    AGENT_PASS_AUDIT(5, "SMS_152288028", "尊敬的${name}，感謝您對xxx的支持。您提交的代理商申請已審核通過！請重新登錄獲取新的會員訊息。"),

    // 代理商商品被購買通知
    NOTIFY_BUY(6, "SMS_152288372", "尊敬的${name}，感謝您對xxx的支持。與您有關的訂單${orderNumber}已生成，客户${buyerName}，提货${prodName}，數量${buyNum}，剩餘庫存為${stockNum}。"),

    // 代理商新增分銷通知
    ADD_DISTRIBUTOR(7, "SMS_152283192", "尊敬的${name}，感谢您對xxx的支持。您有新增綁定客戶資料生成：客户${userName}，聯絡方式${mobilePhone}，聯絡地址${addr}，合計共有客戶${number}名。"),

    // 代理商解除與分銷商合作通知
    UNBINDING(8, "SMS_152283198", "尊敬的${name}，感謝您對xxx的支持。您已成功解除與此客戶的綁定關係：客户${userName}，聯絡方式${mobilePhone}，聯絡地址${addr}，現合計共有客户${number}名。"),

    // 代理商補貨訂單模板
    AGENT_BUY_SUCCESS(9, "SMS_152288475", "尊敬的${name}，感謝您對xxx的支持。您的補貨訂單${orderNumber}已完成入庫，${prodName}剩餘庫存為${stockNum}。"),

    // 普用用戶下單成功通知
    USER_BUY_SUCCESS(10, "SMS_152288329", "親愛的客戶，感謝您對xxx的支持。您的訂單${orderNumber}已支付成功。我们會盡快出貨!");

    private Integer num;

    private String templateCode;

    private String content;

    public Integer value() {
        return num;
    }

    SmsType(Integer num, String templateCode, String content) {
        this.num = num;
        this.templateCode = templateCode;
        this.content = content;
    }

    public static SmsType instance(Integer value) {
        SmsType[] enums = values();
        for (SmsType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public String getContent() {
        return this.content;
    }
}
