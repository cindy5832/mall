package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    // 獲取用於頁面表單展現的category列表 根據seq排序
    List<Category> tableCategory(Long shopId);

    // 保存分類、品牌、參數
    void saveCategory(Category category);

    // 修改品牌分類、品牌、參數
    void updateByCategory(Category category);

    // 刪除分類、品牌、參數、以及類別對應圖片
    void deleteCategory(Long categoryId);

    // 根據商店id和層級獲取商品分類
    List<Category> treeSelect(Long shopId, int grade);
}
