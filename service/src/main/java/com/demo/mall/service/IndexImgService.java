package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.IndexImg;

import java.util.List;

public interface IndexImgService extends IService<IndexImg> {

    // 刪除圖片緩存
    void removeIndexImgCache();

    // 根據id列表刪除圖片
    void deleteIndexImgByIds(Long[] ids);

    // 獲取全部圖片列表
    List<IndexImg> listIndexImg();
}
