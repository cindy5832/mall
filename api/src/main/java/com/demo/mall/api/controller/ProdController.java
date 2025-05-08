package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.app.dto.ProductDto;
import com.demo.mall.bean.app.dto.TagProductDto;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.bean.model.Transport;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.Json;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.SkuService;
import com.demo.mall.service.TransportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prod")
@Tag(name = "prod", description = "商品api")
public class ProdController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private TransportService transportService;

    @GetMapping("/pageProd")
    @Operation(summary = "prod-page", description = "根據分類id獲取所有商品訊息")
    public ServerResponseEntity<IPage<ProductDto>> prodList(@RequestParam(value = "categoryId") Long categoryId, PageParam<ProductDto> page) {
        IPage<ProductDto> productPage = productService.pageByCategoryId(page, categoryId);
        return ServerResponseEntity.success(productPage);
    }

    @GetMapping("/prodInfo")
    @Operation(summary = "prodInfo", description = "根據商品Id獲取商品訊息")
    public ServerResponseEntity<ProductDto> prodInfo(@RequestParam Long prodId) {
        Product product = productService.getProductByProdId(prodId);
        if (product == null) {
            return ServerResponseEntity.success();
        }
        List<Sku> skuList = skuService.listByProdId(prodId);
        // 啟用的sku列表
        List<Sku> useSkuList = skuList.stream().filter(sku -> sku.getStatus() == 1).toList();
        product.setSkuList(useSkuList);
        ProductDto productDto = BeanUtil.copyProperties(product, ProductDto.class);

        // 商品配送方式
        Product.DeliveryModeVO deliveryModeVO = Json.parseObject(product.getDeliveryMode(), Product.DeliveryModeVO.class);
        // 有商店配送的方式，且存在運費模板，才返回運費模板訊息，供前端查看
        if (deliveryModeVO.getHasShopDelivery() && product.getDeliveryTemplateId() != null) {
            Transport transportAndAllItems = transportService.getTransportAndAllItems(product.getDeliveryTemplateId());
            productDto.setTransport(transportAndAllItems);
        }
        return ServerResponseEntity.success(productDto);
    }

    @GetMapping("/lastestProdPage")
    @Operation(summary = "latestProd", description = "新品推薦列表")
    public ServerResponseEntity<IPage<ProductDto>> lastedProdPage(PageParam<ProductDto> page) {
        return ServerResponseEntity.success(productService.pageByPutAwayTime(page));
    }


    @GetMapping("prodListByTagId")
    @Operation(summary = "prod-list-by-Tag", description = "通過分組標籤獲取商品列表")
    public ServerResponseEntity<IPage<ProductDto>> prodListByTagId(@RequestParam(value = "tagId") Long tagId, PageParam<ProductDto> page) {
        return ServerResponseEntity.success(productService.pageByTagId(page, tagId));
    }

    @GetMapping("/moreBuyProdList")
    @Operation(summary = "best-sell", description = "獲取銷售量最多的商品列表")
    public ServerResponseEntity<IPage<ProductDto>> moreBuyProdList(PageParam<ProductDto> page) {
        IPage<ProductDto> productDtoIPage = productService.moreBuyProdList(page);
        return ServerResponseEntity.success(productDtoIPage);
    }

    @GetMapping("/tagProdList")
    @Operation(summary = "tag-prodList", description = "獲取首頁所有標籤商品")
    public ServerResponseEntity<List<TagProductDto>> getTagProdList() {
        List<TagProductDto> productDtoList = productService.tagProdList();
        return ServerResponseEntity.success(productDtoList);
    }

}
