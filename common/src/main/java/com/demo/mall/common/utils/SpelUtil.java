package com.demo.mall.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

// 解析spel表達式
public class SpelUtil {

    /**
     * @description: 支持 #p0 參數索引的表達式解析
     * @param: [rootObject 根對象, spel 表達式, method 目標方法, args 方法入參]
     * @return java.lang.String 解析後返回的字符串
     **/
    public static String parse(Object rootObject, String spel, Method method, Object[] args) {
        if (StrUtil.isBlank(spel)) {
            return StrUtil.EMPTY;
        }
        // 獲取被攔截方法參數名列表
        StandardReflectionParameterNameDiscoverer standardReflectionParameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        String[] parameterNames = standardReflectionParameterNameDiscoverer.getParameterNames(method);
        if(ArrayUtil.isEmpty(parameterNames)){
            return spel;
        }

        // 使用spel進行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // spel上下文
        StandardEvaluationContext context = new MethodBasedEvaluationContext(rootObject, method, args, standardReflectionParameterNameDiscoverer);
        // 將方法參數放入spel上下文中
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return parser.parseExpression(spel).getValue(context, String.class);
    }
}
