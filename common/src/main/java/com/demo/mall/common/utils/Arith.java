package com.demo.mall.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Arith {

    // 默認除法運算精準度
    private static final int DEF_DIV_SCALE = 2;

    // 此類不能實體化
    private Arith() {
    }

    // 提供無診確的加法運算
    public static double add(double v1, double v2) {
        String s1 = Double.toString(v1);
        String s2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.add(b2).doubleValue();
    }

    // 提供無診確的減法運算
    public static double sub(double v1, double v2) {
        String s1 = Double.toString(v1);
        String s2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.subtract(b2).doubleValue();
    }

    // 提供無診確的乘法運算
    public static double mul(double v1, double v2) {
        String s1 = Double.toString(v1);
        String s2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        String s1 = Double.toString(v1);
        String s2 = Double.toString(v2);
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.divide(b2, scale, RoundingMode.HALF_EVEN).doubleValue();
    }


    // 提供相對精準的除法，除不盡時，精確到小數點以後2位，以後數字四捨五入
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    // 提供精確的小數點四捨五入處理
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        String s = Double.toString(v);
        BigDecimal b = new BigDecimal(s);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static double add(BigDecimal b1, BigDecimal b2, BigDecimal b3) {
        return b1.add(b2).add(b3).doubleValue();
    }

    public static double add(BigDecimal preDepositPrice, BigDecimal finalPrice) {
        return preDepositPrice.add(finalPrice).doubleValue();
    }
}
