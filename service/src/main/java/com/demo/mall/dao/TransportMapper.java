package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.TransfeeFree;
import com.demo.mall.bean.model.Transport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransportMapper extends BaseMapper<Transport> {

    // 根據運費模板id獲取運費項目和運費城市
    Transport getTransportAndTransfeeAndTranscity(@Param("id") Long id);

    // 運費模板id獲得免運城市及免運項
    List<TransfeeFree> getTransfeeFreeAndTranscityFreeByTransportId(@Param("transportId") Long transportId);

    // 根據運費模板id刪除運費模板
    void deleteTransports(@Param("ids")Long[] ids);
}
