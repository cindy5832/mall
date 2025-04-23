package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.param.OrderParam;
import com.demo.mall.common.utils.PageAdapter;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.dao.OrderMapper;
import com.demo.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public IPage<Order> pageOrderDetailByOrderParam(PageParam<Order> page, OrderParam orderParam) {
        page.setRecords(orderMapper.listOrdersDetailByParam(new PageAdapter(page), orderParam));
        page.setTotal(orderMapper.countOrderDetail(orderParam));
        return page;
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderMapper.getOrderByOrderNmuber(orderNumber);
    }

    @Override
    @Transactional
    public void delivery(Order order) {
        orderMapper.updateById(order);
    }

    @Override
    public List<Order> listOrdersDetailByOrder(Order order, Date startTime, Date endTime) {
        return orderMapper.listOrdersDetailByOrder(order, startTime, endTime);
    }
}
