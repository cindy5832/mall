package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Transcity;
import com.demo.mall.bean.model.TranscityFree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TranscityMapper extends BaseMapper<Transcity> {

    // 插入運費項中的城市
    void insertTranscities(@Param("transcities") List<Transcity> transcities);

    // 插入運費
    void insertTranscityFrees(@Param("transcityFrees") List<TranscityFree> transcityFrees);

    // 根據運費id刪除運費項
    void deleteBatchByTransfeeIds(@Param("transfeeIds") List<Long> transfeeIds);

    // 根據運費免運項id刪除
    void deleteBatchByTransfeeFreeIds(@Param("transfeeFreeIds") List<Long> transfeeFreeIds);
}
