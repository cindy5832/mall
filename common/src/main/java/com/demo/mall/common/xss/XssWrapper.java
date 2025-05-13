package com.demo.mall.common.xss;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class XssWrapper extends HttpServletRequestWrapper {

    public XssWrapper(HttpServletRequest request) {
        super(request);
    }

    // 對數組參數進行特殊字符過濾
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXss(values[i]);
        }
        return encodedValues;
    }

    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String && StrUtil.isNotBlank((String) value)) {
            return cleanXss((String) value);
        }
        return value;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StrUtil.isBlank(value)) {
            return value;
        }
        return cleanXss(value);
    }

    private String cleanXss(String value) {
        return XssUtil.clean(value);
    }
}
