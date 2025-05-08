package com.demo.mall.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.app.dto.ProductDto;
import com.demo.mall.bean.app.dto.UserCollectionDto;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.UserCollection;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.UserCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/p/user/collection")
@Tag(name = "user-collection")
public class UserCollectionController {
    @Autowired
    private UserCollectionService userCollectionService;

    @Autowired
    private ProductService productService;

    @GetMapping("/page")
    @Operation(summary = "collection-page", description = "根據用戶id獲取收藏數據")
    public ServerResponseEntity<IPage<UserCollectionDto>> getUserCollectionPage(PageParam page) {
        return ServerResponseEntity.success(userCollectionService.getUserCollectionDtoPageByUserId(page, SecurityUtils.getUser().getUserId()));
    }

    @GetMapping("/isCollection")
    @Operation(summary = "isCollection", description = "根據商品id獲取該商品是否在收藏夾中")
    public ServerResponseEntity<Boolean> isCollection(Long prodId) {
        if (productService.count(new LambdaQueryWrapper<Product>()
                .eq(Product::getProdId, prodId)) < 1) {
            throw new ShopException("該商品不存在");
        }
        return ServerResponseEntity.success(userCollectionService.count(
                new LambdaQueryWrapper<UserCollection>()
                        .eq(UserCollection::getProdId, prodId)
                        .eq(UserCollection::getUserId, SecurityUtils.getUser().getUserId())) > 0);
    }

    @PostMapping("/addOrCancel")
    @Operation(summary = "add-or-cancel", description = "傳入prodId，若商品未被收藏則收藏商品，若已存在取消收藏")
    public ServerResponseEntity addOrCancel(@RequestBody Long prodId) {
        if (Objects.isNull(productService.getProductByProdId(prodId))) {
            throw new ShopException("該商品不存在");
        }
        String userId = SecurityUtils.getUser().getUserId();
        if (userCollectionService.count(new LambdaQueryWrapper<UserCollection>()
                .eq(UserCollection::getUserId, userId)
                .eq(UserCollection::getProdId, prodId)) > 0) {
            userCollectionService.remove(
                    new LambdaQueryWrapper<UserCollection>()
                            .eq(UserCollection::getUserId, userId)
                            .eq(UserCollection::getProdId, prodId)
            );
        } else {
            UserCollection userCollection = new UserCollection();
            userCollection.setCreateTime(new Date());
            userCollection.setUserId(userId);
            userCollection.setProdId(prodId);
            userCollectionService.save(userCollection);
        }
        return ServerResponseEntity.success();
    }

    @GetMapping("/count")
    @Operation(summary = "collection-count", description = "查詢用戶收藏商品數量")
    public ServerResponseEntity<Long> collectionCount() {
        String userId = SecurityUtils.getUser().getUserId();
        return ServerResponseEntity.success(userCollectionService.count(
                new LambdaQueryWrapper<UserCollection>()
                        .eq(UserCollection::getUserId, userId)
        ));
    }

    @GetMapping("/prods")
    @Operation(summary = "collection-list", description = "獲取用戶收藏商品列表")
    public ServerResponseEntity<IPage<ProductDto>> getUserCollectionList(PageParam page) {
        String userId = SecurityUtils.getUser().getUserId();
        IPage<ProductDto> productDtoIPage = productService.collectionProds(page, userId);
        return ServerResponseEntity.success(productDtoIPage);
    }


}
