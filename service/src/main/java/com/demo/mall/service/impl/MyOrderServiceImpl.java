package com.demo.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.app.dto.MyOrderDto;
import com.demo.mall.bean.model.Order;
import com.demo.mall.common.utils.PageAdapter;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.dao.OrderMapper;
import com.demo.mall.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyOrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements MyOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public IPage<MyOrderDto> pageMyOrderByUserIdAndStatus(PageParam<MyOrderDto> page, String useId, Integer status) {
        page.setRecords(orderMapper.listMyOrderByUserIdAndStatus(new PageAdapter(page), useId, status));
        page.setTotal(orderMapper.countMyOrderByUserIdAndStatus(useId, status));
        return page;
    }
}
