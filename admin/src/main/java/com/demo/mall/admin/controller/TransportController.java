package com.demo.mall.admin.controller;

import com.anji.captcha.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.Transport;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.TransportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shop/transport")
@Tag(name = "shop-transport", description = "運費管理")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('shop:tansport:page')")
    @Operation(summary = "transport-page", description = "分頁獲取運費")
    public ServerResponseEntity<IPage<Transport>> page(Transport transport, PageParam<Transport> page) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        IPage<Transport> transportIPage = transportService.page(page, new LambdaQueryWrapper<Transport>()
                .eq(Transport::getShopId, shopId)
                .like(StringUtils.isNotBlank(transport.getTransName()), Transport::getTransName, transport.getTransName())
        );
        return ServerResponseEntity.success(transportIPage);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('shop:transport:info')")
    @Operation(summary = "shop-transport-info", description = "根據id查詢運費訊息")
    public ServerResponseEntity<Transport> info(@PathVariable("id") Long id) {
        Transport transport = transportService.getTransportAndAllItems(id);
        return ServerResponseEntity.success(transport);
    }

    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('shop:transport:save')")
    @Operation(summary = "shop-transport-save", description = "保存運費資訊")
    public ServerResponseEntity save(@RequestBody Transport transport) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        transport.setShopId(shopId);
        transport.setCreateTime(new Date());
        transportService.insertTransportAndTransfee(transport);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('shop:transport:update')")
    @Operation(summary = "shop-transport-update", description = "修改運費")
    public ServerResponseEntity update(@RequestBody Transport transport) {
        transportService.updateTransportAndTransfee(transport);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('shop:transport:delete')")
    @Operation(summary = "shop-transport-delete", description = "刪除運費")
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        transportService.deleteTransportAndTransfeeAndTranscity(ids);
        // 刪除運費模板的緩存
        for (Long id : ids) {
            transportService.removeTransportAndAllItemsCache(id);
        }
        return ServerResponseEntity.success();
    }

    @GetMapping("/list")
    @Operation(summary = "shop-transport-list", description = "獲取運費模板列表")
    public ServerResponseEntity<List<Transport>> list() {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        List<Transport> list = transportService.list(
                new LambdaQueryWrapper<Transport>()
                        .eq(Transport::getShopId, shopId)
        );
        return ServerResponseEntity.success(list);
    }
}
