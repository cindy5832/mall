package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.OrderItem;
import com.demo.mall.dao.OrderItemMapper;
import com.demo.mall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Cacheable(cacheNames = "OrderItems", key = "#orderNumber")
    public List<OrderItem> getOrderItemsByOrderNumber(String orderNumber) {
        return orderItemMapper.listByOrderNumber(orderNumber);
    }
}
