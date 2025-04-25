package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Transfee;
import com.demo.mall.bean.model.TransfeeFree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransfeeMapper extends BaseMapper<Transfee> {

    // 插入運費項
    void insertTransfees(List<Transfee> transfees);

    // 插入免運項
    void insertTransfeeFrees(List<TransfeeFree> transfeeFrees);

    // 根據運費模板id刪除
    void deleteByTrnasportId(@Param("transportId") Long transportId);

    // 根據運費模板id刪除免運項目
    void deleteTransfeeFreeByTransportId(@Param("transportId") Long transportId);

}
