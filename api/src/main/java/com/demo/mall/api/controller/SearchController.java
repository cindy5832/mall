package com.demo.mall.api.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.demo.mall.bean.dto.HotSearchDto;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.HotSearchService;
import com.demo.mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "search", description = "搜尋")
public class SearchController {
    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
    private ProductService productService;

    @GetMapping("/hotSearchByShopId")
    @Operation(summary = "hot-search-by-shopId", description = "根據商店id、熱搜數量獲取熱搜")
    public ServerResponseEntity<List<HotSearchDto>> hotSearchByShopId(Long shopId, Integer number, Integer sort) {
        List<HotSearchDto> list = hotSearchService.getHotSearchDtoByShopId(shopId);
        return getListResponseEntity(number, sort, list);
    }

    @GetMapping("/hotSearch")
    @Operation(summary = "hot-search", description = "根據商店id查看全局熱搜")
    public ServerResponseEntity<List<HotSearchDto>> hotSearch(Integer number, Integer sort) {
        List<HotSearchDto> list = hotSearchService.getHotSearchDtoByShopId(0L);
        return getListResponseEntity(number, sort, list);
    }

    private ServerResponseEntity<List<HotSearchDto>> getListResponseEntity(Integer number, Integer sort, List<HotSearchDto> list) {
        if (sort == null || sort == 0) {
            Collections.shuffle(list);
        }
        if (!CollectionUtil.isNotEmpty(list) || list.size() < number) {
            return ServerResponseEntity.success(list);
        }
        return ServerResponseEntity.success(list.subList(0, number));
    }
}
