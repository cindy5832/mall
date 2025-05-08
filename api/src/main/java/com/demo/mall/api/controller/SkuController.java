package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.mall.bean.app.dto.SkuDto;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.SkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sku")
@Tag(name = "sku", description = "sku-api")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("/getSkuList")
    @Operation(summary = "sku-list", description = "通過ProdId獲取商品全部規格列表")
    public ServerResponseEntity<List<SkuDto>> getSkuListByProdId(Long prodId) {
        List<Sku> skus = skuService.list(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getStatus, 1)
                .eq(Sku::getIsDelete, 0)
                .eq(Sku::getProdId, prodId));
        List<SkuDto> skuDtoList = BeanUtil.copyToList(skus, SkuDto.class);
        return ServerResponseEntity.success(skuDtoList);
    }
}
