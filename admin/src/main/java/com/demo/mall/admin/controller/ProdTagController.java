package com.demo.mall.admin.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.ProdTag;
import com.demo.mall.common.annotation.SysOperationLog;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.ProdTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/prod/prodTag")
@Tag(name = "prod-prodTag", description = "商品分組")
public class ProdTagController {
    @Autowired
    private ProdTagService prodTagService;

    @GetMapping("/page")
    @Operation(summary = "prodTag-page", description = "商品分組分頁查詢")
    public ServerResponseEntity<IPage<ProdTag>> page(PageParam<ProdTag> page, ProdTag prodTag) {
        IPage<ProdTag> tagIPage = prodTagService.page(page, new LambdaQueryWrapper<ProdTag>()
                .eq(prodTag.getStatus() != null, ProdTag::getStatus, prodTag.getStatus())
                .like(prodTag.getTitle() != null, ProdTag::getTitle, prodTag.getTitle())
                .orderByDesc(ProdTag::getStatus, ProdTag::getCreateTime)
        );
        return ServerResponseEntity.success(tagIPage);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "prodTag-info", description = "根據id查詢商品分類標籤")
    public ServerResponseEntity<ProdTag> info(@PathVariable("id") Long id) {
        return ServerResponseEntity.success(prodTagService.getById(id));
    }

    @SysOperationLog("新增商品分組標籤")
    @PostMapping("/addTag")
    @PreAuthorize("@pms.hasPermission('prod:prodTag:save')")
    @Operation(summary = "prodTag-save", description = "新增商品分組標籤")
    public ServerResponseEntity save(@RequestBody @Valid ProdTag prodTag) {
        List<ProdTag> list = prodTagService.list(
                new LambdaQueryWrapper<ProdTag>()
                        .like(ProdTag::getTitle, prodTag.getTitle())
        );
        if (CollectionUtil.isNotEmpty(list)) {
            throw new ShopException("分組標籤名稱已存在，不能添加相同標籤");
        }
        prodTag.setIsDefault(0);
        prodTag.setProdCount(0L);
        prodTag.setCreateTime(new Date());
        prodTag.setUpdateTime(new Date());
        prodTag.setShopId(SecurityUtils.getSysUser().getShopId());
        prodTagService.removeProdTag();
        return ServerResponseEntity.success(prodTagService.save(prodTag));
    }


    @SysOperationLog("修改商品分組標籤")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('prod:prodTag:update')")
    @Operation(summary = "prodTag-update", description = "修改商品分組標籤")
    public ServerResponseEntity<Boolean> updateById(@RequestBody @Valid ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        prodTagService.removeProdTag();
        return ServerResponseEntity.success(prodTagService.updateById(prodTag));
    }

    @SysOperationLog("刪除商品分組標籤")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@pms.hasPermission('prod:prodTag:delete')")
    @Operation(summary = "prod-prodTag", description = "刪除商品分組標籤")
    public ServerResponseEntity<Boolean> removeById(@PathVariable("id") Long id) {
        ProdTag prodTag = prodTagService.getById(id);
        if (prodTag.getIsDefault() != 0) {
            throw new ShopException("默認標籤不能刪除");
        }
        prodTagService.removeProdTag();
        return ServerResponseEntity.success(prodTagService.removeById(id));
    }

    @GetMapping("/listTagList")
    @Operation(summary = "prodTag-listTagList", description = "獲取商品分組標籤列表")
    public ServerResponseEntity<List<ProdTag>> listTagList() {
        return ServerResponseEntity.success(prodTagService.listProdTag());
    }
}
