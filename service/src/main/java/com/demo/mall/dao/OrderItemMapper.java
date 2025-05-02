package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper extends BaseMapper<OrderItem> {

    // 根據訂單編號獲取訂單項目
    List<OrderItem> listByOrderNumber(@Param("orderNumber") String orderNumber);

    // 插入訂單項目
    void insertBatch(List<OrderItem> orderItems);
}
