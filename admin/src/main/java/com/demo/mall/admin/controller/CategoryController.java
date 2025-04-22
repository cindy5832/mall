package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.mall.bean.model.Category;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Tag(name = "prod-category", description = "商品分類管理")
@RestController
@RequestMapping("/prod/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "prod-category-table", description = "獲取菜單頁面的分類表")
    @GetMapping("/table")
    @PreAuthorize("@pms.hasPermission('prod:category:page')")
    public ServerResponseEntity<List<Category>> table() {
        List<Category> categoryList = categoryService.tableCategory(SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success(categoryList);
    }

    @Operation(summary = "prod-categary-info", description = "獲取分類訊息")
    @GetMapping("/info/{categoryId}")
    public ServerResponseEntity<Category> info(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return ServerResponseEntity.success(category);
    }

    @SysOperationLog("保存分類")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('prod:category:save')")
    @Operation(summary = "prod-category-save", description = "保存分類訊息")
    public ServerResponseEntity save(@RequestBody Category category) {
        category.setShopId(SecurityUtils.getSysUser().getShopId());
        category.setRecTime(new Date());
        Category categoryName = categoryService.getOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryName, category.getCategoryName())
                        .eq(Category::getShopId, category.getShopId()));
        if (Objects.nonNull(categoryName)) {
            throw new ShopException("類別名稱已存在!");
        }
        categoryService.saveCategory(category);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("更新分類")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('prod:category:update')")
    @Operation(summary = "prod-category-update", description = "更新分類訊息")
    public ServerResponseEntity<String> update(@RequestBody Category category) {
        category.setShopId(SecurityUtils.getSysUser().getShopId());
        if (Objects.equals(category.getParentId(), category.getCategoryId())) {
            return ServerResponseEntity.showFailMsg("分類的上級不能是自己本身");
        }
        Category categoryName = categoryService.getOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryName, category.getCategoryName())
                        .eq(Category::getShopId, category.getShopId())
                        .ne(Category::getCategoryId, category.getCategoryId())
        );
        if (categoryName != null) {
            throw new ShopException("類別名稱已存在!");
        }
        Category categoryDb = categoryService.getById(category.getCategoryId());
        // 如果將分類狀態從下線改為正常 需判斷上級狀態
        if (Objects.equals(categoryDb.getStatus(), 0)
                && Objects.equals(category.getStatus(), 1)
                && !Objects.equals(category.getParentId(), 0L)) {
            Category parent = categoryService.getOne(new LambdaQueryWrapper<Category>()
                    .eq(Category::getCategoryId, category.getCategoryId()));
            if (Objects.isNull(parent) || Objects.equals(parent.getStatus(), 0)) {
                // 修改失敗 上級分類不存在 或是 不為正常狀態
                throw new ShopException("修改失敗，上級分類不存在或為不正常狀態");
            }
        }
        categoryService.updateByCategory(category);
        return ServerResponseEntity.success();
    }

    @SysOperationLog("刪除分類")
    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("@pms.hasPermission('prod:category:delete')")
    @Operation(summary = "prod-category-delete", description = "刪除分類訊息")
    public ServerResponseEntity delete(@PathVariable("categoryId") Long categoryId) {
        if (categoryService.count(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getParentId, categoryId)) > 0) {
            return ServerResponseEntity.showFailMsg("請刪除子分類，再刪除該分類");
        }
        categoryService.deleteCategory(categoryId);
        return ServerResponseEntity.success();
    }

    @GetMapping("/listCategory")
    @Operation(summary = "admin-category-all-list", description = "顯示所有的類別")
    public ServerResponseEntity<List<Category>> listCategory() {
        List<Category> list = categoryService.list(
                new LambdaQueryWrapper<Category>()
                        .le(Category::getGrade, 2)
                        .eq(Category::getShopId, SecurityUtils.getSysUser().getShopId())
                        .orderByAsc(Category::getSeq)
        );
        return ServerResponseEntity.success(list);
    }

    public ServerResponseEntity<List<Category>> listProdCategory() {
        List<Category> categories = categoryService.treeSelect(SecurityUtils.getSysUser().getShopId(), 2);
        return ServerResponseEntity.success(categories);
    }
}
