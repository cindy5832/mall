package com.demo.mall.common.utils;

import jakarta.servlet.http.HttpServletRequest;

// IP 輔助工具
public class IpHelper {
    private static final String UNKNOWN = "unknown";

    // 得到用戶的真實地址，若有多個則取第一個
    public static String getIpAddr() {
        HttpServletRequest  request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = ip.split(",");
        return ips[0].trim();
    }
}
