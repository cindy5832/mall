package com.demo.mall.admin.controller;

import com.demo.mall.bean.model.Delivery;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/delivery")
@Tag(name = "admin-delivery", description = "物流管理")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/list")
    @Operation(summary = "admin-delivery-list", description = "物流列表")
    public ServerResponseEntity<List<Delivery>> page() {
        List<Delivery> list = deliveryService.list();
        return ServerResponseEntity.success(list);
    }
}
