package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.enums.ProdPropRule;
import com.demo.mall.bean.model.ProdProp;
import com.demo.mall.bean.model.ProdPropValue;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.ProdPropService;
import com.demo.mall.service.ProdPropValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/prod/spec")
@Tag(name = "prod-spec", description = "商品規格管理")
public class SpecController {
    @Autowired
    private ProdPropService prodPropService;

    @Autowired
    private ProdPropValueService prodPropValueService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('prod:spec:page')")
    @Operation(summary = "prod-spec-page", description = "分頁獲取商品規格")
    public ServerResponseEntity<IPage<ProdProp>> page(ProdProp prodProp, PageParam<ProdProp> page) {
        prodProp.setRule(ProdPropRule.SPEC.value());
        prodProp.setShopId(SecurityUtils.getSysUser().getShopId());
        IPage<ProdProp> list = prodPropService.pagePropAndValue(prodProp, page);
        return ServerResponseEntity.success(list);
    }

    @GetMapping("/list")
    @Operation(summary = "prod-spec-list", description = "獲取所有商品規格")
    public ServerResponseEntity<List<ProdProp>> list() {
        List<ProdProp> list = prodPropService.list(
                new LambdaQueryWrapper<ProdProp>()
                        .eq(ProdProp::getRule, ProdPropRule.SPEC.value())
                        .eq(ProdProp::getShopId, SecurityUtils.getSysUser().getShopId())
        );
        return ServerResponseEntity.success(list);
    }

    @GetMapping("/listSpecValue/{specId}")
    @Operation(summary = "prod-spec-value", description = "根據規格id獲取值")
    public ServerResponseEntity<List<ProdPropValue>> listSpecValue(@PathVariable("specId") Long specId) {
        List<ProdPropValue> list = prodPropValueService.list(
                new LambdaQueryWrapper<ProdPropValue>()
                        .eq(ProdPropValue::getPropId, specId)
        );
        return ServerResponseEntity.success(list);
    }

    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('prod:spec:save')")
    @Operation(summary = "prod-spece-save", description = "保持商品規格")
    public ServerResponseEntity save(@RequestBody @Valid ProdProp prodProp) {
        prodProp.setRule(ProdPropRule.SPEC.value());
        prodProp.setShopId(SecurityUtils.getSysUser().getShopId());
        prodPropService.saveProdProdAndValues(prodProp);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('prod:spec:update')")
    @Operation(summary = "prod-spec-update", description = "修改商品規格")
    public ServerResponseEntity update(@RequestBody @Valid ProdProp prodProp) {
        ProdProp dbProdPop = prodPropService.getById(prodProp.getPropId());
        if (!Objects.equals(dbProdPop.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            throw new ShopException("沒有權限獲取該商品規格訊息");
        }
        prodProp.setRule(ProdPropRule.SPEC.value());
        prodProp.setShopId(SecurityUtils.getSysUser().getShopId());
        prodPropService.updateProdPropAndValues(prodProp);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@pms.hasPermission('prod:spec:delete')")
    @Operation(summary = "prod-spec-delete", description = "刪除商品規格")
    public ServerResponseEntity delete(@PathVariable Long id) {
        prodPropService.deleteProdPropAndValues(id, ProdPropRule.SPEC.value(), SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }

    @GetMapping("/listSpecMaxValueId")
    @Operation(summary = "prod-spec-maxValueId", description = "獲取規格最大值的id")
    public ServerResponseEntity<Long> listSpecMaxValueId() {
        ProdPropValue prodPropValue = prodPropValueService.getOne(
                new LambdaQueryWrapper<ProdPropValue>()
                        .orderByDesc(ProdPropValue::getValueId)
                        .last("limit 1")
        );
        return ServerResponseEntity.success(Objects.isNull(prodPropValue) ? 0L : prodPropValue.getValueId());
    }
}
