package com.demo.mall.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.bean.param.ProductParam;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.Json;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.BasketService;
import com.demo.mall.service.ProdTagReferenceService;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.SkuService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/prod/prod")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProdTagReferenceService prodTagReferenceService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('prod:prod:page')")
    @Operation(summary = "prod-page", description = "分頁獲取商品資訊")
    public ServerResponseEntity<IPage<Product>> page(PageParam<Product> page, ProductParam product) {
        IPage<Product> productIPage = productService.page(page, new LambdaQueryWrapper<Product>()
                .like(StrUtil.isNotBlank(product.getProdName()), Product::getProdName, product.getProdName())
                .eq(Product::getShopId, SecurityUtils.getSysUser().getShopId())
                .eq(product.getStatus() != null, Product::getStatus, product.getStatus())
                .orderByDesc(Product::getPutawayTime));
        return ServerResponseEntity.success(productIPage);
    }

    @GetMapping("/info/{prodId}")
    @PreAuthorize("@pms.hasPermission('prod:prod:info')")
    @Operation(summary = "prod-info", description = "根據商品id獲取商品資訊")
    public ServerResponseEntity<Product> info(@PathVariable("prodId") Long prodId) {
        Product product = productService.getProductByProdId(prodId);
        if (!Objects.equals(product.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            throw new ShopException("沒有權限獲取該商品訊息");
        }
        List<Long> listTagId = prodTagReferenceService.listTagIdByProdId(prodId);
        product.setTagList(listTagId);
        return ServerResponseEntity.success(product);
    }

    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('prod:prod:save')")
    @Operation(summary = "prod-save", description = "保存商品資訊")
    public ServerResponseEntity<String> save(@RequestBody @Valid ProductParam productParam) {
        checkParam(productParam);

        Product product = BeanUtil.copyProperties(productParam, Product.class);
        product.setDeliveryMode(Json.toJsonString(productParam.getDeliveryModeVo()));
        product.setShopId(SecurityUtils.getSysUser().getShopId());
        product.setUpdateTime(new Date());
        if (product.getStatus() == 1) {
            product.setPutawayTime(new Date());
        }
        product.setCreateTime(new Date());
        productService.saveProduct(product);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('prod:prod:update')")
    @Operation(summary = "prod-update", description = "商品修改")
    public ServerResponseEntity<String> update(@RequestBody @Valid ProductParam productParam) {
        checkParam(productParam);

        Product dbProduct = productService.getProductByProdId(productParam.getProdId());
        if (!Objects.equals(dbProduct.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            return ServerResponseEntity.showFailMsg("沒有權限修改非本店商品訊息");
        }
        List<Sku> dbSku = skuService.listByProdId(dbProduct.getProdId());

        Product product = BeanUtil.copyProperties(productParam, Product.class);
        product.setUpdateTime(new Date());
        product.setDeliveryMode(Json.toJsonString(productParam.getDeliveryModeVo()));
        if (dbProduct.getStatus() == 0 || productParam.getStatus() == 1) {
            product.setPutawayTime(new Date());
        }
        dbProduct.setSkuList(dbSku);
        productService.updateProduct(product, dbProduct);

        List<String> userIds = basketService.listUserIdByProdId(product.getProdId());

        for (String userId : userIds) {
            basketService.removeShopCartItemsCacheByUserId(userId);
        }
        for (Sku sku : dbSku) {
            skuService.removeSkuCacheBySkuId(sku.getSkuId(), sku.getProdId());
        }
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@pms.hasPermission('prod:prod:delete')")
    @Operation(summary = "prod-delete", description = "刪除商品")
    public ServerResponseEntity batchDelete(@RequestBody Long[] prodIds) {
        for (Long prodId : prodIds) {
            deleteProd(prodId);
        }
        return ServerResponseEntity.success();
    }

    @PutMapping("/updateStatus")
    @PreAuthorize("@pms.hasPermission('prod:prod:status')")
    @Operation(summary = "prod-status", description = "修改商品狀態")
    public ServerResponseEntity changeProdStatus(@RequestParam Long prodId, @RequestParam Integer status) {
        Product product = new Product();
        product.setProdId(prodId);
        product.setStatus(status);
        if (status == 1) {
            product.setPutawayTime(new Date());
        }

        productService.updateById(product);
        productService.removeProductCacheByProdId(prodId);
        List<String> userIds = basketService.listUserIdByProdId(prodId);

        for (String userId : userIds) {
            basketService.removeShopCartItemsCacheByUserId(userId);
        }
        return ServerResponseEntity.success();
    }

    private ServerResponseEntity deleteProd(Long prodId) {
        Product dbProduct = productService.getProductByProdId(prodId);
        if (!Objects.equals(dbProduct.getShopId(), SecurityUtils.getSysUser().getShopId())) {
            throw new ShopException("沒有權限獲取該商店的商品訊息");
        }

        List<Sku> dbSku = skuService.listByProdId(dbProduct.getProdId());
        // 刪除商品
        productService.removeProductByProdId(prodId);

        for (Sku sku : dbSku) {
            skuService.removeSkuCacheBySkuId(sku.getSkuId(), sku.getProdId());
        }
        List<String> userIds = basketService.listUserIdByProdId(prodId);
        for (String userId : userIds) {
            basketService.removeShopCartItemsCacheByUserId(userId);
        }
        return ServerResponseEntity.success();
    }


    private void checkParam(ProductParam productParam) {
        if (CollectionUtils.isEmpty(productParam.getTagList())) {
            throw new ShopException("請輸入商品分組");
        }
        Product.DeliveryModeVO deliveryModeVO = productParam.getDeliveryModeVo();
        boolean hasDeliveryMode = deliveryModeVO != null && (deliveryModeVO.getHasShopDelivery() || deliveryModeVO.getHasUserPickUp());
        if (!hasDeliveryMode) {
            throw new ShopException("請選擇配送方式");
        }
        List<Sku> skuList = productParam.getSkuList();
        boolean isAllUnUse = true;
        for (Sku sku : skuList) {
            if (sku.getStatus() == 1) {
                isAllUnUse = false;
            }
        }
        if (!isAllUnUse) {
            throw new ShopException("至少需要啟用一種商品規格");
        }
    }

}
