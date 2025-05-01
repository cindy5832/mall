package com.demo.mall.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.app.dto.*;
import com.demo.mall.bean.enums.OrderStatus;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.model.OrderItem;
import com.demo.mall.bean.model.ShopDetail;
import com.demo.mall.bean.model.UserAddrOrder;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/p/myOrder")
@Tag(name = "api-order", description = "我的訂單")
public class MyOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private MyOrderService myOrderService;

    @Autowired
    private ShopDetailService shopDetailService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserAddrOrderService userAddrOrderService;

    @GetMapping("/orderDetail")
    @Operation(summary = "orderNumber", description = "根據訂單號獲取訂單訊息")
    public ServerResponseEntity<OrderShopDto> orderDetail(@RequestParam(value = "orderNumber") String orderNumber) {
        String useId = SecurityUtils.getUser().getUserId();
        OrderShopDto orderShopDto = new OrderShopDto();

        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (order == null) {
            throw new ShopException("該訂單不存在");
        }
        if (!Objects.equals(order.getUserId(), useId)) {
            throw new RuntimeException("無權限獲取該訂單訊息");
        }

        ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(order.getShopId());
        UserAddrOrder userAddrOrder = userAddrOrderService.getById(order.getAddrOrderId());
        UserAddrDto userAddrDto = BeanUtil.copyProperties(userAddrOrder, UserAddrDto.class);
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderNumber(orderNumber);
        List<OrderItemDto> orderItemDtoList = BeanUtil.copyToList(orderItems, OrderItemDto.class);

        orderShopDto.setShopId(shopDetail.getShopId());
        orderShopDto.setShopName(shopDetail.getShopName());
        orderShopDto.setActualTotal(order.getActualTotal());
        orderShopDto.setUserAddrDto(userAddrDto);
        orderShopDto.setOrderItemDtos(orderItemDtoList);
        orderShopDto.setTransfee(order.getFreightAmount());
        orderShopDto.setReduceAmount(order.getReduceAmount());
        orderShopDto.setCreateTime(order.getCreateTime());
        orderShopDto.setRemarks(order.getRemarks());
        orderShopDto.setStatus(order.getStatus());

        double total = 0.0;
        Integer totalNum = 0;
        for (OrderItemDto orderItem : orderShopDto.getOrderItemDtos()) {
            total = Arith.add(total, orderItem.getProductTotalAmount());
            totalNum += orderItem.getProdCount();
        }
        orderShopDto.setTotal(total);
        orderShopDto.setTotalNum(totalNum);

        return ServerResponseEntity.success(orderShopDto);
    }

    @GetMapping("/myOrder")
    @Operation(summary = "order-list", description = "根據訂單狀態獲取訂單列表訊息，status = 0時，獲取所有訂單")
    @Parameters(@Parameter(name = "status", description = "訂單狀態 1待付款 2待出貨 3待取貨 4待評價 5成功 6失敗"))
    public ServerResponseEntity<IPage<MyOrderDto>> myOrder(@RequestParam Integer status, PageParam<MyOrderDto> page) {
        String useId = SecurityUtils.getUser().getUserId();
        IPage<MyOrderDto> myOrderDtoIPage = myOrderService.pageMyOrderByUserIdAndStatus(page, useId, status);
        return ServerResponseEntity.success(myOrderDtoIPage);
    }

    @PutMapping("/cancel/{orderNumber}")
    @Operation(summary = "cancel-order", description = "根據訂單號取消訂單")
    public ServerResponseEntity<String> cancel(@PathVariable("orderNumber") String orderNumber) {
        String useId = SecurityUtils.getUser().getUserId();
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (!Objects.equals(order.getUserId(), useId)) {
            throw new ShopException("沒有權限獲取該訂單訊息");
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.UNPAY.value())) {
            throw new ShopException("訂單已支付，無法取消訂單");
        }
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderNumber(orderNumber);
        // 取消訂單
        orderService.cancelOrders(Collections.singletonList(order));
        // 清除緩存
        for (OrderItem orderItem : orderItems) {
            productService.removeProductCacheByProdId(orderItem.getProdId());
            skuService.removeSkuCacheBySkuId(orderItem.getSkuId(), orderItem.getProdId());
        }
        return ServerResponseEntity.success();
    }

    @PutMapping("/receipt/{orderNumber}")
    @Operation(summary = "check-order-receive", description = "根據訂單號確認收貨")
    public ServerResponseEntity<String> receipt(@PathVariable("orderNumber") String orderNumber) {
        String useId = SecurityUtils.getUser().getUserId();
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (!Objects.equals(order.getUserId(), useId)) {
            throw new ShopException("沒有權限獲取該訂單訊息");
        }
        if (!Objects.equals(order.getStatus(), OrderStatus.CONSIGNMENT.value())) {
            throw new ShopException("訂單未發貨，無法確認收貨");
        }
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderNumber(orderNumber);
        order.setOrderItems(orderItems);

        // 確認收貨
        orderService.confirmOrder(Collections.singletonList(order));

        for (OrderItem orderItem : orderItems) {
            productService.removeProductCacheByProdId(orderItem.getProdId());
            skuService.removeSkuCacheBySkuId(orderItem.getSkuId(), orderItem.getProdId());
        }
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/{orderNumber}")
    @Operation(summary = "api-delete-order", description = "根據訂單刪除訂單")
    public ServerResponseEntity<String> delete(@PathVariable("orderNumber") String orderNumber) {
        String useId = SecurityUtils.getUser().getUserId();

        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (!Objects.equals(order.getUserId(), useId)) {
            throw new ShopException("沒有權限獲取該訂單訊息");
        }

        if (!Objects.equals(order.getStatus(), OrderStatus.CLOSE.value()) && !Objects.equals(order.getStatus(), OrderStatus.SUCCESS.value())) {
            throw new ShopException("訂單未完成或未關閉，無法刪除訂單");
        }

        orderService.deleteOrders(Collections.singletonList(order));
        return ServerResponseEntity.success("刪除成功");
    }

    @GetMapping("/orderCount")
    @Operation(summary = "api-order-count", description = "獲取我的訂單數量")
    public ServerResponseEntity<OrderCountData> getOrderCount() {
        String userId = SecurityUtils.getUser().getUserId();
        OrderCountData orderCountData = orderService.getOrderCount(userId);
        return ServerResponseEntity.success(orderCountData);
    }

}

