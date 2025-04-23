package com.demo.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.PickAddr;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ResponseEnum;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.PickAddrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/shop/pickAddr")
@Tag(name = "shop-pickAddr", description = "取貨地址")
public class PickAddrController {

    @Autowired
    private PickAddrService pickAddrService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('shop:pickAddr:page')")
    @Operation(summary = "shop-pickAddr-page", description = "取貨地址分頁查詢")
    public ServerResponseEntity<IPage<PickAddr>> page(PickAddr pickAddr, PageParam<PickAddr> page) {
        IPage<PickAddr> pickAddrIPage = pickAddrService.page(page, new LambdaQueryWrapper<PickAddr>()
                .like(StrUtil.isNotBlank(pickAddr.getAddrName()), PickAddr::getAddrName, pickAddr.getAddrName())
                .orderByDesc(PickAddr::getAddrId));
        return ServerResponseEntity.success(pickAddrIPage);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('shop:pickAddr:info')")
    @Operation(summary = "shop-pickAddr-info", description = "取貨地址資訊")
    public ServerResponseEntity<PickAddr> info(@PathVariable("id") Long id) {
        PickAddr pickAddr = pickAddrService.getById(id);
        return ServerResponseEntity.success(pickAddr);
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('shop:pickAddr:update')")
    @Operation(summary = "shop-pickAddr", description = "修改取貨地址")
    public ServerResponseEntity update(@Valid @RequestBody PickAddr pickAddr) {
        PickAddr dbPickAddr = pickAddrService.getById(pickAddr.getAddrId());
        if (!Objects.equals(dbPickAddr.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            throw new ShopException(ResponseEnum.UNAUTHORIZED);
        }
        pickAddrService.updateById(pickAddr);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('shop:pickAddr:delete')")
    @Operation(summary = "shop-pickAddr-delete", description = "刪除取貨地址")
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        pickAddrService.removeByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }

}
