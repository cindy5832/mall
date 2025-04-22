package com.demo.mall.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.IndexImg;
import com.demo.mall.bean.model.Product;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.IndexImgService;
import com.demo.mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/admin/IndexImg")
@Tag(name = "admin-indexImg", description = "商店主頁輪播圖管理")
public class IndexImgController {

    @Autowired
    private IndexImgService indexImgService;
    @Autowired
    private ProductService productService;


    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('admin:indexImg:page')")
    @Operation(summary = "admin-indexImg-page", description = "商店輪播圖分頁查詢")
    public ServerResponseEntity<IPage<IndexImg>> page(IndexImg indexImg, PageParam<IndexImg> page) {
        IPage<IndexImg> indexImgIPage = indexImgService.page(page, new LambdaQueryWrapper<IndexImg>()
                .eq(indexImg.getStatus() != null, IndexImg::getStatus, indexImg.getStatus())
                .orderByAsc(IndexImg::getSeq)
        );
        return ServerResponseEntity.success(indexImgIPage);
    }

    @GetMapping("/info/{imgId}")
    @PreAuthorize("@pms.hasPermission('admin:indexImg:info')")
    @Operation(summary = "admin-indexImg-info", description = "商店輪播圖資訊查詢")
    public ServerResponseEntity<IndexImg> info(@PathVariable("imgId") Long imgId) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        IndexImg indexImg = indexImgService.getOne(
                new LambdaQueryWrapper<IndexImg>()
                        .eq(IndexImg::getShopId, shopId)
                        .eq(IndexImg::getImgId, imgId)
        );
        if (Objects.nonNull(indexImg.getRelation())) {
            Product product = productService.getProductByProdId(indexImg.getRelation());
            indexImg.setPic(product.getPic());
            indexImg.setProdName(product.getProdName());
        }
        return ServerResponseEntity.success(indexImg);
    }

    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('admin:indexImg:save')")
    @Operation(summary = "admin-indexImg-save", description = "保存商店輪播圖")
    public ServerResponseEntity save(@RequestBody @Valid IndexImg indexImg) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        indexImg.setShopId(shopId);
        indexImg.setUploadTime(new Date());
        checkProdStatus(indexImg);
        indexImgService.save(indexImg);
        indexImgService.removeIndexImgCache();
        return ServerResponseEntity.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('admin:indexImg:update')")
    @Operation(summary = "admin-indexImg-update", description = "修改商店首頁輪播圖")
    public ServerResponseEntity update(@RequestBody @Valid IndexImg indexImg) {
        checkProdStatus(indexImg);
        indexImgService.updateById(indexImg);
        indexImgService.removeIndexImgCache();
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('admin:indexImg:delete')")
    @Operation(summary = "admin-indexImg-delete", description = "刪除商店首頁輪播圖")
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        indexImgService.deleteIndexImgByIds(ids);
        indexImgService.removeIndexImgCache();
        return ServerResponseEntity.success();
    }


    private void checkProdStatus(IndexImg indexImg) {
        if (!Objects.equals(indexImg.getType(), 0)) {
            return;
        }
        if (Objects.isNull(indexImg.getRelation())) {
            throw new ShopException("請選擇商品");
        }
        Product product = productService.getById(indexImg.getRelation());
        if (Objects.isNull(product)) {
            throw new ShopException("商品訊息不存在");
        }
        if (!Objects.equals(product.getStatus(), 1)) {
            throw new ShopException("該商品尚未上架，請選擇其他商品");
        }
    }

}
