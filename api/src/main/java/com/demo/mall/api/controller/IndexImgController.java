package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.demo.mall.bean.app.dto.IndexImgDto;
import com.demo.mall.bean.model.IndexImg;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.IndexImgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/indexImgs")
@Tag(name = "api-indexImg", description = "首頁輪播圖")
public class IndexImgController {

    @Autowired
    private IndexImgService indexImgService;

    @GetMapping
    @Operation(summary = "indexImg", description = "獲取首頁輪播圖列表訊息")
    public ServerResponseEntity<List<IndexImgDto>> getIndexImgs() {
        List<IndexImg> indexImgs = indexImgService.listIndexImg();
        List<IndexImgDto> indexImgDtos = BeanUtil.copyToList(indexImgs, IndexImgDto.class);
        return ServerResponseEntity.success(indexImgDtos);

    }
}
