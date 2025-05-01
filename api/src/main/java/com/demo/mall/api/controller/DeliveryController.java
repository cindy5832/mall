package com.demo.mall.api.controller;

import cn.hutool.http.HttpUtil;
import com.demo.mall.bean.app.dto.DeliveryDto;
import com.demo.mall.bean.model.Delivery;
import com.demo.mall.bean.model.Order;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.Json;
import com.demo.mall.service.DeliveryService;
import com.demo.mall.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
@Tag(name = "api-delivery", description = "查看物流api")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/check")
    @Operation(summary = "api-check", description = "根據訂單號查詢物流")
    public ServerResponseEntity<DeliveryDto> checkDelivery(@RequestParam String orderNumber) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        Delivery delivery = deliveryService.getById(order.getDvyId());
        String url = delivery.getQueryUrl().replace("{dvyFlowId}", order.getDvyFlowId());
        String deliveryJson = HttpUtil.get(url);

        DeliveryDto deliveryDto = Json.parseObject(deliveryJson, DeliveryDto.class);
        deliveryDto.setDvyFlowId(order.getDvyFlowId());
        deliveryDto.setCompanyHomeUrl(delivery.getCompanyHomeUrl());
        deliveryDto.setCompanyName(delivery.getDvyName());
        return ServerResponseEntity.success(deliveryDto);
    }
}
