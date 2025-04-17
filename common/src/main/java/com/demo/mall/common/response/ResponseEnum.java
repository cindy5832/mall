/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.common.response;

public enum ResponseEnum {
    /**
     * OK: OK
     * SHOW_FAIL: 用於直接顯示提示用戶的錯誤，內容由輸入內容決定
     * SHOW_SUCCESS: 用於直接顯示提示系統的成功，內容由輸入內容決定
     * UNAUTHORIZED: 未授權
     * EXCEPTION: 伺服器出問題
     * METHOD_ARGUMENT_NOT_VALID: 方法參數沒有校驗，內容由輸入內容決定
     **/
    OK("00000", "ok"),
    SHOW_FAIL("A00001", ""),
    SHOW_SUCCESS("A00002", ""),
    UNAUTHORIZED("A00004", "Unauthorized"),
    EXCEPTION("A00005", "伺服器出問題"),
    METHOD_ARGUMENT_NOT_VALID("A00014", "方法參數沒有校驗");

    private final String code;

    private final String msg;

    public String value() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }

}
