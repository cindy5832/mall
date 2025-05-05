package com.demo.mall.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.mall.bean.event.PaySuccessOrderEvent;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.model.OrderSettlement;
import com.demo.mall.bean.param.PayParam;
import com.demo.mall.bean.pay.PayInfoDto;
import com.demo.mall.common.enums.PayType;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.dao.OrderMapper;
import com.demo.mall.dao.OrderSettlementMapper;
import com.demo.mall.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettlementMapper orderSettlementMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Snowflake snowflake;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayInfoDto pay(String userId, PayParam payParam) {
        // 不同的訂單號的產品名稱
        StringBuilder prodName = new StringBuilder();
        // 支付單號
        String payNo = String.valueOf(snowflake.nextId());
        String[] orderNumbers = payParam.getOrderNumbers().split(StrUtil.COMMA);
        // 修改訂單訊息
        for (String orderNumber : orderNumbers) {
            OrderSettlement orderSettlement = new OrderSettlement();
            orderSettlement.setPayNo(payNo);
            orderSettlement.setPayType(payParam.getPayType());
            orderSettlement.setUserId(userId);
            orderSettlement.setOrderNumber(orderNumber);
            orderSettlementMapper.updateByOrderNumberAndUserId(orderSettlement);

            Order order = orderMapper.getOrderByOrderNmuber(orderNumber);
            prodName.append(order.getProdName()).append(StrUtil.COMMA);
        }

        // 除了orderNumber不同其他相同
        List<OrderSettlement> settlements = orderSettlementMapper.getSettlementByPayNo(payNo);
        // 應支付金額
        double payAmount = 0;
        for (OrderSettlement settlement : settlements) {
            payAmount = Arith.add(payAmount, settlement.getPayAmount());
        }

        prodName.substring(0, Math.min(100, prodName.length() - 1));

        PayInfoDto payInfoDto = new PayInfoDto();
        payInfoDto.setBody(prodName.toString());
        payInfoDto.setPayAmount(payAmount);
        payInfoDto.setPayNo(payNo);
        return payInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> paySuccess(String payNo, String bizPayNo) {
        List<OrderSettlement> orderSettlements = orderSettlementMapper.selectList(
                new LambdaQueryWrapper<OrderSettlement>()
                        .eq(OrderSettlement::getPayNo, payNo)
        );
        OrderSettlement settlement = orderSettlements.get(0);
        if (settlement.getPayStatus() == 1) {
            throw new ShopException("訂單已支付");
        }
        if (orderSettlementMapper.updateToPay(payNo, settlement.getVersion()) < 1) {
            throw new ShopException("結算訊息已更改");
        }

        List<String> orderNumbers = orderSettlements.stream().map(OrderSettlement::getOrderNumber).toList();

        // 將訂單改為已支付狀態
        orderMapper.updateByToPaySuccess(orderNumbers, PayType.WECHATPAY.value());

        List<Order> orders = orderNumbers.stream().map(
                orderNumber -> orderMapper.getOrderByOrderNmuber(orderNumber)
        ).toList();
        eventPublisher.publishEvent(new PaySuccessOrderEvent(orders));
        return orderNumbers;
    }
}
