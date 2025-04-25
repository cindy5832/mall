package com.demo.mall.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.model.ShopDetail;
import com.demo.mall.bean.param.ShopDetailParam;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.ShopDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop/shopDetail")
@Tag(name = "shop/shopDetail")
public class ShopDetailController {

    @Autowired
    private ShopDetailService shopDetailService;

    @PutMapping("/isDistribution")
    @Operation(summary = "shop-isDistribution", description = "修改分銷開關")
    public ServerResponseEntity updateIsDistribution(@RequestParam Integer isDistribution) {
        ShopDetail shopDetail = new ShopDetail();
        shopDetail.setShopId(SecurityUtils.getSysUser().getShopId());
        shopDetail.setIsDistribution(isDistribution);
        shopDetailService.updateById(shopDetail);
        // 更新玩刪除緩存
        shopDetailService.removeShopDetailCacheByShopId(SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success();
    }

    @GetMapping("/info")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:info')")
    @Operation(summary = "shopDetail-info", description = "獲取商店詳情")
    public ServerResponseEntity<ShopDetail> info() {
        ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(SecurityUtils.getSysUser().getShopId());
        return ServerResponseEntity.success(shopDetail);
    }

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:page')")
    @Operation(summary = "shopDetail-page", description = "分頁獲取商店資訊")
    public ServerResponseEntity<IPage<ShopDetail>> page(ShopDetail shopDetail, PageParam<ShopDetail> page) {
        IPage<ShopDetail> shopDetailIPage = shopDetailService.page(page, new LambdaQueryWrapper<ShopDetail>()
                .like(StrUtil.isNotBlank(shopDetail.getShopName()), ShopDetail::getShopName, shopDetail.getShopName())
                .orderByDesc(ShopDetail::getShopId));
        return ServerResponseEntity.success(shopDetailIPage);
    }

    @GetMapping("/info/{shopId}")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:info')")
    @Operation(summary = "shopDetail-info", description = "根據商店id獲得商店訊息")
    public ServerResponseEntity<ShopDetail> info(@PathVariable("shopId") Long shopId) {
        ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
        return ServerResponseEntity.success(shopDetail);
    }

    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:save')")
    @Operation(summary = "shopDetail-save", description = "保存商店詳情")
    public ServerResponseEntity save(@Valid ShopDetailParam shopDetailParam) {
        ShopDetail shopDetail = BeanUtil.copyProperties(shopDetailParam, ShopDetail.class);
        shopDetail.setCreateTime(new Date());
        shopDetail.setShopStatus(1);
        shopDetailService.save(shopDetail);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:update')")
    @Operation(summary = "shopDetail-update", description = "修改商店訊息")
    public ServerResponseEntity update(@Valid ShopDetailParam shopDetailParam) {
        ShopDetail dbShopDetail = shopDetailService.getShopDetailByShopId(shopDetailParam.getShopId());
        ShopDetail shopDetail = BeanUtil.copyProperties(shopDetailParam, ShopDetail.class);
        shopDetail.setUpdateTime(new Date());
        shopDetailService.updateShopDetail(shopDetail);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/delet/{id}")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:delete')")
    @Operation(summary = "shopDetail-delete", description = "刪除商店")
    public ServerResponseEntity delete(@PathVariable("id") Long id) {
        shopDetailService.deleteShopDetailByShopId(id);
        return ServerResponseEntity.success();
    }

    @PutMapping("/update/shopStatus")
    @PreAuthorize("@pms.hasPermission('shop:shopDetail:shopStatus')")
    @Operation(summary = "shopDetail-updateShopStatus", description = "更新商店狀態")
    public ServerResponseEntity updateShopStatus(@RequestParam Long shopId, @RequestParam Integer status) {
        ShopDetail shopDetail = new ShopDetail();
        shopDetail.setShopId(shopId);
        shopDetail.setShopStatus(status);
        shopDetailService.updateById(shopDetail);
        // 更新完後刪除緩存
        shopDetailService.removeShopDetailCacheByShopId(shopId);
        return ServerResponseEntity.success();
    }

    @GetMapping("/listShopName")
    @Operation(summary = "shopDetail-shopName", description = "獲得所有商店名稱")
    public ServerResponseEntity<List<ShopDetail>> listShopName() {
        List<ShopDetail> shopDetailList = shopDetailService.list()
                .stream()
                .map((dbShopDetail) -> {
                    ShopDetail shopDetail = new ShopDetail();
                    shopDetail.setShopId(dbShopDetail.getShopId());
                    shopDetail.setShopName(dbShopDetail.getShopName());
                    return shopDetail;
                }).toList();
        return ServerResponseEntity.success(shopDetailList);
    }
}
