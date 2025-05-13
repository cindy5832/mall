package com.demo.mall.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.system.model.SysConfig;
import com.demo.mall.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/config")
@Tag(name = "system-config", description = "系統配置訊息")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys:config:page')")
    @Operation(summary = "config-page", description = "所有配置訊息")
    public ServerResponseEntity<IPage<SysConfig>> page(String paramKey, PageParam<SysConfig> page) {
        IPage<SysConfig> sysConfigIPage = sysConfigService.page(page,
                new LambdaQueryWrapper<SysConfig>().like(StrUtil.isNotBlank(paramKey), SysConfig::getParamKey, paramKey));
        return ServerResponseEntity.success(sysConfigIPage);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('sys:config:info')")
    @Operation(summary = "config-info", description = "配置訊息")
    public ServerResponseEntity<SysConfig> info(@PathVariable("id") Long id) {
        SysConfig sysConfig = sysConfigService.getById(id);
        return ServerResponseEntity.success(sysConfig);
    }

    @SysOperationLog("保存配置")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('sys:config:save')")
    @Operation(summary = "config-save", description = "保存配置")
    public ServerResponseEntity save(@RequestBody @Valid SysConfig sysConfig) {
        sysConfigService.save(sysConfig);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("修改配置")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('sys:config:update')")
    @Operation(summary = "config-update", description = "修改配置")
    public ServerResponseEntity update(@RequestBody @Valid SysConfig sysConfig) {
        sysConfigService.updateById(sysConfig);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("刪除配置")
    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('sys:config:delete')")
    @Operation(summary = "config-delete", description = "刪除配置")
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);
        return ServerResponseEntity.success();
    }
}
