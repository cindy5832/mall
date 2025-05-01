package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.IndexImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexImgMapper extends BaseMapper<IndexImg> {

    // 根據id列表刪除圖片
    void deleteIndexImgByIds(@Param("ids") Long[] ids);

    // 獲取圖片列表
    List<IndexImg> listIndexImg();
}
