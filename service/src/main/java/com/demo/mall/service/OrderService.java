package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.OrderCountData;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.param.OrderParam;
import com.demo.mall.common.utils.PageParam;

import java.util.Date;
import java.util.List;

public interface OrderService extends IService<Order> {

    // 根據參數分頁獲取訂單
    IPage<Order> pageOrderDetailByOrderParam(PageParam<Order> page, OrderParam orderParam);

    // 根據訂單號獲取訂單資訊
    Order getOrderByOrderNumber(String orderNumber);

    // 出貨
    void delivery(Order order);

    // 根據參數獲取訂單列表
    List<Order> listOrdersDetailByOrder(Order order, Date startTime, Date endTime);

    // 取消訂單
    void cancelOrders(List<Order> orders);

    // 訂單確認收貨
    void confirmOrder(List<Order> orders);

    // 刪除訂單
    void deleteOrders(List<Order> orders);

    // 根據用戶id獲取各狀態的訂單數量
    OrderCountData getOrderCount(String userId);
}
