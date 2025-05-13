package com.demo.mall.system.constant;

// 菜單類型
public enum MenuType {

    // 目錄
    CATALOG(0),

    // 菜單
    MENU(1),

    // 按鈕
    BUTTON(2);

    private int value;

    MenuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
