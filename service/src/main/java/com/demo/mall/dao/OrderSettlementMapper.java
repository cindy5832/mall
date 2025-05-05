package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.OrderSettlement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSettlementMapper extends BaseMapper<OrderSettlement> {

    // 更新訂單結算
    void updateByOrderNumberAndUserId(@Param("orderSettlement")OrderSettlement orderSettlement);

    // 根據支付單號獲取結算訊息
    List<OrderSettlement> getSettlementByPayNo(@Param("payNo")String payNo);

    // 更新結算訊息為已支付
    int updateToPay(@Param("payNo") String payNo, @Param("version") Integer version);
}
