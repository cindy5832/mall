package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.app.dto.OrderCountData;
import com.demo.mall.bean.app.dto.ShopCartOrderMergerDto;
import com.demo.mall.bean.event.CancelOrderEvent;
import com.demo.mall.bean.event.ReceiptOrderEvent;
import com.demo.mall.bean.event.SubmitOrderEvent;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.model.OrderItem;
import com.demo.mall.bean.param.OrderParam;
import com.demo.mall.common.utils.PageAdapter;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.dao.OrderItemMapper;
import com.demo.mall.dao.OrderMapper;
import com.demo.mall.dao.ProductMapper;
import com.demo.mall.dao.SkuMapper;
import com.demo.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private OrderItemMapper orderItemMapper;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrders(List<Order> orders) {
        orderMapper.cancelOrders(orders);
        List<OrderItem> allOrderItems = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> orderItems = order.getOrderItems();
            allOrderItems.addAll(orderItems);
            eventPublisher.publishEvent(new CancelOrderEvent(order));
        }
        if (CollectionUtil.isEmpty(allOrderItems)) return;

        Map<Long, Integer> prodCollect = new HashMap<>(16);
        Map<Long, Integer> skuCollect = new HashMap<>(16);
        allOrderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getProdId))
                .forEach((prodId, orderItems) -> {
                    int prodTotalNum = orderItems.stream().mapToInt(OrderItem::getProdCount).sum();
                    prodCollect.put(prodId, prodTotalNum);
                });
        productMapper.returnStock(prodCollect);

        allOrderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getProdId))
                .forEach((prodId, orderItems) -> {
                    int prodTotalNum = orderItems.stream().mapToInt(OrderItem::getProdCount).sum();
                    skuCollect.put(prodId, prodTotalNum);
                });
        skuMapper.returnStock(skuCollect);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(List<Order> orders) {
        orderMapper.confirmOrder(orders);
        for (Order order : orders) {
            eventPublisher.publishEvent(new ReceiptOrderEvent(order));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrders(List<Order> orders) {
        orderMapper.deleteOrders(orders);
    }

    @Override
    public OrderCountData getOrderCount(String userId) {
        return orderMapper.getOrderCount(userId);
    }

    @Override
    public List<Order> listOrderAndOrderItems(Integer orderStatus, DateTime lessThanUpdateTime) {
        return orderMapper.listOrderAndOrderItem(orderStatus, lessThanUpdateTime);
    }

    @Override
    @Cacheable(cacheNames = "ConfirmOrderCache", key = "#userId")
    public ShopCartOrderMergerDto putConfirmOrderCache(String userId, ShopCartOrderMergerDto shopCartOrderMergerDto) {
        return shopCartOrderMergerDto;
    }

    @Override
    @Cacheable(cacheNames = "ConfirmOrderCache", key = "#userId")
    public ShopCartOrderMergerDto getConfirmOrderCache(String userId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Order> submit(String userId, ShopCartOrderMergerDto mergerOrder) {
        List<Order> orderList = new ArrayList<>();
        // 通過事務提交訂單
        eventPublisher.publishEvent(new SubmitOrderEvent(mergerOrder, orderList));
        // 插入訂單
        saveBatch(orderList);
        List<OrderItem> orderItems = orderList.stream().flatMap(
                        order -> order.getOrderItems().stream())
                .toList();
        // 插入訂單向，返回主鍵
        orderItemMapper.insertBatch(orderItems);
        return orderList;
    }

    @Override
    @CacheEvict(cacheNames = "ConfirmOrderCache", key = "#userId")
    public void removeConfirmOrderCache(String userId) {

    }
}
