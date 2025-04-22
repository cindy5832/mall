package com.demo.mall.system.aspect;

import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson.JSON;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.utils.IpHelper;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.system.model.SysLog;
import com.demo.mall.system.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint joinPoint, SysOperationLog sysLog) throws Throwable {
        long startTime = SystemClock.now();
        // 執行方法
        Object result = joinPoint.proceed();
        // 執行時間 (毫秒)
        long time = SystemClock.now() - startTime;

        SysLog sysLogModel = new SysLog();
        if (sysLog != null) {
            // @SysOperationLog 的描述
            sysLogModel.setOperation(sysLog.value());
        }

        // 請求方法名稱
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sysLogModel.setMethod(className + "." + methodName + "()");

        // 請求參數
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        sysLogModel.setParams(params);

        // 設置IP地址
        sysLogModel.setIp(IpHelper.getIpAddr());

        // 用戶名
        String userName = SecurityUtils.getSysUser().getUserName();
        sysLogModel.setUsername(userName);

        // 創建時間及運行時長
        sysLogModel.setTime(time);
        sysLogModel.setCreateDate(new Date());

        // 保存系統日誌
        sysLogService.save(sysLogModel);
        return result;
    }


}
