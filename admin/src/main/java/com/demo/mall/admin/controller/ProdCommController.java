package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.ProdComm;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.ProdCommService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/prod/prodComm")
public class ProdCommController {

    @Autowired
    private ProdCommService prodCommService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('prod:prodComm:page')")
    @Operation(summary = "prod-prodComm-page", description = "商品評論分頁查詢")
    public ServerResponseEntity<IPage<ProdComm>> getProdCommPage(PageParam<ProdComm> page, ProdComm prodComm) {
        return ServerResponseEntity.success(prodCommService.getProdCommPage(page, prodComm));
    }

    @GetMapping("/info/{prodCommId}")
    @PreAuthorize("@pms.hasPermission('prod:prodComm:info')")
    @Operation(summary = "prodComm-info", description = "通過id查詢商品評論")
    public ServerResponseEntity<ProdComm> getProdCommInfo(@PathVariable Long prodCommId) {
        return ServerResponseEntity.success(prodCommService.getById(prodCommId));
    }

    @SysOperationLog("新增商品評論")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('prod:prodComm:save')")
    @Operation(summary = "prodComm-save", description = "新增商品評論")
    public ServerResponseEntity<Boolean> saveProdComm(@RequestBody @Valid ProdComm prodComm) {
        return ServerResponseEntity.success(prodCommService.save(prodComm));
    }

    @SysOperationLog("修改商品評論")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('prod:prodComm:update')")
    public ServerResponseEntity<Boolean> updateProdComm(@RequestBody @Valid ProdComm prodComm) {
        prodComm.setReplyTime(new Date());
        return ServerResponseEntity.success(prodCommService.updateById(prodComm));
    }

    @SysOperationLog("刪除商品評論")
    @DeleteMapping("/delete/{prodCommId}")
    @PreAuthorize("@pms.hasPermission('prod:prodComm:delete')")
    @Operation(summary = "prodComm-delete", description = "透過id刪除商品評論")
    public ServerResponseEntity<Boolean> deleteProdComm(@PathVariable Long prodCommId) {
        return ServerResponseEntity.success(prodCommService.removeById(prodCommId));
    }

}
