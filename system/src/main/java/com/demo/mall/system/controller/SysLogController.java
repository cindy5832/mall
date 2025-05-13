package com.demo.mall.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.system.model.SysLog;
import com.demo.mall.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/log")
@Tag(name = "sys-log", description = "系統日誌")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys:log:page')")
    @Operation(summary = "log-page", description = "系統日誌列表")
    public ServerResponseEntity<IPage<SysLog>> page(SysLog sysLog, PageParam<SysLog> page) {
        IPage<SysLog> sysLogIPage = sysLogService.page(page
                , new LambdaQueryWrapper<SysLog>()
                        .like(StrUtil.isNotBlank(sysLog.getUsername()), SysLog::getUsername, sysLog.getUsername())
                        .like(StrUtil.isNotBlank(sysLog.getOperation()), SysLog::getOperation, sysLog.getOperation()));
        return ServerResponseEntity.success(sysLogIPage);
    }
}
