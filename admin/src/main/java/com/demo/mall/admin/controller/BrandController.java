package com.demo.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.Brand;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "admin-brand", description = "品牌管理")
@RestController
@RequestMapping("/admin/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "admin-brand-page", description = "品牌分頁查詢")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:brand:page')")
    public ServerResponseEntity<IPage<Brand>> page(Brand brand, PageParam<Brand> page) {
        IPage<Brand> brands = brandService.page(
                page, new LambdaQueryWrapper<Brand>()
                        .like(StrUtil.isNotBlank(brand.getBrandName()), Brand::getBrandName, brand.getBrandName())
                        .orderByAsc(Brand::getFirstChar)
        );
        return ServerResponseEntity.success(brands);
    }

    @Operation(summary = "admin-brand-info", description = "品牌訊息")
    @GetMapping("/info/{id}")
    @PreAuthorize("@pms.hasPermission('admin:brand:info')")
    public ServerResponseEntity<Brand> info(@PathVariable("id") Long id) {
        Brand brand = brandService.getById(id);
        return ServerResponseEntity.success(brand);
    }

    @Operation(summary = "admin-brand-save", description = "保存品牌訊息")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('admin:brand:save')")
    public ServerResponseEntity save(@Valid @RequestBody Brand brand) {
        Brand dbBrand = brandService.getByBrandName(brand.getBrandName());
        if (dbBrand != null) {
            throw new ShopException("該品牌名稱已存在");
        }
        brandService.save(brand);
        return ServerResponseEntity.success();
    }

    @Operation(summary = "admin-brand-update", description = "修改品牌資訊")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:brand:update')")
    public ServerResponseEntity update(@Valid Brand brand) {
        Brand dbBrand = brandService.getByBrandName(brand.getBrandName());
        if (dbBrand != null && !Objects.equals(dbBrand.getBrandId(), brand.getBrandId())) {
            throw new ShopException("該品牌名稱已存在");
        }
        brandService.updateById(brand);
        return ServerResponseEntity.success();
    }

    @Operation(summary = "admin-brand-delete", description = "刪除品牌資訊")
    @DeleteMapping("delete/{id}")
    @PreAuthorize("@pms.hasPermission('admin:brand:delete')")
    public ServerResponseEntity delete(@PathVariable("id") Long id) {
        brandService.deleteByBrand(id);
        return ServerResponseEntity.success();
    }

}
