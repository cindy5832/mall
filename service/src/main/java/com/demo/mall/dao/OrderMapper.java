package com.demo.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.param.OrderParam;
import com.demo.mall.common.utils.PageAdapter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    // 根據分頁參數和訂單參數獲取訂單列表
    List<Order> listOrdersDetailByParam(@Param("adapter") PageAdapter adapter, @Param("orderParam") OrderParam orderParam);

    // 根據訂單參數獲取訂單數
    long countOrderDetail(@Param("orderParam")OrderParam orderParam);

    // 根據訂單號獲取訂單資訊
    Order getOrderByOrderNmuber(@Param("orderNumber")String orderNumber);

    // 根據參數和時間獲取訂單列表
    List<Order> listOrdersDetailByOrder(@Param("order") Order order, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
