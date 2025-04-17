package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.enums.ProdPropRule;
import com.demo.mall.bean.model.ProdProp;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.ProdPropService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/admin/attribute")
@Tag(name = "admin-attribute", description = "參數管理")
public class AttributeController {

    @Autowired
    private ProdPropService prodPropService;

    @Tag(name = "admin-attribute-page")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:attribute:page')")
    public ServerResponseEntity<IPage<ProdProp>> page(ProdProp prodProp, PageParam<ProdProp> page) {
        prodProp.setRule(ProdPropRule.ATTRIBUTE.value());
        prodProp.setShopId(SecurityUtils.getSysUser().getShopId());
        IPage<ProdProp> prodPropIPage = prodPropService.pagePropAndValue(prodProp, page);
        return ServerResponseEntity.success(prodPropIPage);
    }

    @Operation(summary = "admin-attribute-info", description = "獲取參數訊息")
    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('admin:attribute:info')")
    public ServerResponseEntity<ProdProp> info(@PathVariable("id") Long id) {
        ProdProp prodProp = prodPropService.getById(id);
        return ServerResponseEntity.success(prodProp);
    }

    @Operation(summary = "admin-attribute-save", description = "保存參數訊息")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('admin:attribute:save')")
    public ServerResponseEntity save(@Valid @RequestBody ProdProp prodProp) {
        prodProp.setRule(ProdPropRule.ATTRIBUTE.value());
        prodProp.setShopId(SecurityUtils.getSysUser().getShopId());
        prodPropService.saveProdProdAndValues(prodProp);
        return ServerResponseEntity.success();
    }

    @Operation(summary = "admin-attribute-update", description = "修改參數訊息")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:attribute:save')")
    public ServerResponseEntity update(@Valid ProdProp prodProp) {
        ProdProp dbProdProp = prodPropService.getById(prodProp.getPropId());
        Long shopId = SecurityUtils.getSysUser().getShopId();
        if (!Objects.equals(dbProdProp.getPropId(), shopId)) {
            throw new ShopException("沒有權限獲取該商品規格訊息");
        }
        prodProp.setRule(ProdPropRule.ATTRIBUTE.value());
        prodProp.setShopId(shopId);
        prodPropService.updateProdPropAndValues(prodProp);
        return ServerResponseEntity.success();
    }

    @Operation(summary = "admin-attribute-delete", description = "刪除參數訊息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('admin:attribute:delete')")
    public ServerResponseEntity delete(@PathVariable("id") Long id) {
        prodPropService.deleteProdPropAndValues(id, ProdPropRule.ATTRIBUTE.value(), SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }
}
