package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.demo.mall.bean.app.dto.ProdTagDto;
import com.demo.mall.bean.model.ProdTag;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.ProdTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prod/tag")
@Tag(name = "prod-tag", description = "商品分組標籤api")
public class ProdTagController {
    @Autowired
    private ProdTagService prodTagService;

    @GetMapping("/prodTagList")
    @Operation(summary = "prod-tag-list", description = "獲取所有商品分組列表")
    public ServerResponseEntity<List<ProdTagDto>> prodTagList() {
        List<ProdTag> prodTagList = prodTagService.listProdTag();
        List<ProdTagDto> prodTagDtoList = BeanUtil.copyToList(prodTagList, ProdTagDto.class);
        return ServerResponseEntity.success(prodTagDtoList);
    }
}
