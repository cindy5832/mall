package com.demo.mall.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mall.bean.app.dto.MyOrderDto;
import com.demo.mall.bean.app.dto.OrderCountData;
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
    long countOrderDetail(@Param("orderParam") OrderParam orderParam);

    // 根據訂單號獲取訂單資訊
    Order getOrderByOrderNmuber(@Param("orderNumber") String orderNumber);

    // 根據參數和時間獲取訂單列表
    List<Order> listOrdersDetailByOrder(@Param("order") Order order, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    // 根據用戶id和訂單狀態獲取訂單列表
    List<MyOrderDto> listMyOrderByUserIdAndStatus(@Param("adapter") PageAdapter adapter, @Param("userId") String userId, @Param("status") Integer status);

    // 根據用戶id和訂單狀態獲取訂單數量
    Long countMyOrderByUserIdAndStatus(@Param("userId") String userId, @Param("status") Integer status);

    // 取消訂單
    void cancelOrders(@Param("orders") List<Order> orders);

    // 訂單確認收貨
    void confirmOrder(@Param("orders") List<Order> orders);

    // 刪除訂單
    void deleteOrders(@Param("orders") List<Order> orders);

    // 根據用戶id獲取各狀態的訂單數量
    OrderCountData getOrderCount(String userId);

    // 根據參數獲取訂單
    List<Order> listOrderAndOrderItem(@Param("orderStatus") Integer orderStatus, @Param("lessThanUpdateTime") DateTime lessThanUpdateTime);

    // 更新訂單為支付成功
    void updateByToPaySuccess(@Param("orderNumbers") List<String> orderNumbers, @Param("payType") Integer payType);
}
