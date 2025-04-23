package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.OrderItem;

import java.util.List;

public interface OrderItemService extends IService<OrderItem> {

    // 根據訂單編號獲取訂單項目
    List<OrderItem> getOrderItemsByOrderNumber(String orderNumber);
}
