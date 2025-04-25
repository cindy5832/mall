package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.ProdTagReference;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.ProdTagReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generator/prodTagReference")
@Tag(name = "generator-prodTagReference", description = "分組標籤引用")
public class ProdTagReferenceController {

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @GetMapping("/page")
    @Operation(summary = "prodTagReference-page", description = "分組標籤分頁查詢")
    public ServerResponseEntity<IPage<ProdTagReference>> getProdTagReferencePage(PageParam<ProdTagReference> page, ProdTagReference prodTagReference) {
        return ServerResponseEntity.success(prodTagReferenceService.page(page, new LambdaQueryWrapper<ProdTagReference>()));
    }

    @GetMapping("/info/{referenceId}")
    @Operation(summary = "prodTagReference-info", description = "通過id查詢分組標籤資訊")
    public ServerResponseEntity<ProdTagReference> getProdTagReferenceInfo(@PathVariable("referenceId") Long referenceId) {
        return ServerResponseEntity.success(prodTagReferenceService.getById(referenceId));
    }

    @SysOperationLog("新增分組標籤引用")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('generator:prodTagReference:save')")
    @Operation(summary = "prodTagReference-save", description = "新增分組標籤引用")
    public ServerResponseEntity<Boolean> saveProdTagReference(@RequestBody @Valid ProdTagReference prodTagReference) {
        return ServerResponseEntity.success(prodTagReferenceService.save(prodTagReference));
    }

    @SysOperationLog("修改分組標籤引用")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('generator:prodTagReference:update')")
    @Operation(summary = "prodTagReference-update", description = "修改分組標籤")
    public ServerResponseEntity<Boolean> updateProdTagReference(@RequestBody @Valid ProdTagReference prodTagReference) {
        return ServerResponseEntity.success(prodTagReferenceService.updateById(prodTagReference));
    }

    @SysOperationLog("刪除分組標籤引用")
    @DeleteMapping("/delete/{referenceId}")
    @PreAuthorize("@pms.hasPermission('generator:prodTagReference:delete')")
    @Operation(summary = "prodTagReference-delete", description = "刪除分組標籤")
    public ServerResponseEntity<Boolean> deleteProdTagReference(@PathVariable("referenceId") Long referenceId) {
        return ServerResponseEntity.success(prodTagReferenceService.removeById(referenceId));
    }
}
