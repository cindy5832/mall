package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.demo.mall.bean.app.dto.CategoryDto;
import com.demo.mall.bean.model.Category;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryInfo")
    @Operation(summary = "api-categoryInfo", description = "獲取所有產品的分類訊息，最頂層的parentId = 0，莫認為頂層")
    public ServerResponseEntity<List<CategoryDto>> getCategoryInfo(@RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        List<Category> categories = categoryService.listByParentId(parentId);
        List<CategoryDto> categoryDtos = BeanUtil.copyToList(categories, CategoryDto.class);
        return ServerResponseEntity.success(categoryDtos);
    }

}
