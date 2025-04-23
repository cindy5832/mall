package com.demo.mall.admin.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.mall.bean.enums.OrderStatus;
import com.demo.mall.bean.model.Order;
import com.demo.mall.bean.model.OrderItem;
import com.demo.mall.bean.model.UserAddrOrder;
import com.demo.mall.bean.param.DeliveryOrderParam;
import com.demo.mall.bean.param.OrderParam;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.security.admin.util.SecurityUtils;
import com.demo.mall.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/order/order")
@Tag(name = "order-order", description = "訂單管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserAddrOrderService userAddrOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('order:order:page')")
    @Operation(summary = "order-order-page", description = "訂單分頁查詢")
    public ServerResponseEntity<IPage<Order>> page(OrderParam orderParam, PageParam<Order> page) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        orderParam.setShopId(shopId);
        IPage<Order> orderIPage = orderService.pageOrderDetailByOrderParam(page, orderParam);
        return ServerResponseEntity.success(orderIPage);
    }

    @GetMapping("/orderInfo/{orderNumber}")
    @PreAuthorize("@pms.hasPermission('order:order:info')")
    @Operation(summary = "order-info", description = "獲取訂單資訊")
    public ServerResponseEntity<Order> orderInfo(@PathVariable("orderNumber") String orderNumber) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        if (!Objects.equals(shopId, order.getShopId())) {
            throw new ShopException("沒有權限獲取該訂單資訊");
        }
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderNumber(orderNumber);
        order.setOrderItems(orderItems);
        UserAddrOrder userAddrOrder = userAddrOrderService.getById(order.getAddrOrderId());
        order.setUserAddrOrder(userAddrOrder);
        return ServerResponseEntity.success(order);
    }

    @PutMapping("/delivery")
    @PreAuthorize("@pms.hasPermission('order:order:delivery')")
    @Operation(summary = "order-delivery", description = "訂單出貨")
    public ServerResponseEntity delivery(@RequestBody DeliveryOrderParam deliveryOrderParam) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        Order order = orderService.getOrderByOrderNumber(deliveryOrderParam.getOrderNumber());
        if (!Objects.equals(shopId, order.getShopId())) {
            throw new ShopException("沒有權限獲取該訂單資訊");
        }

        Order orderParam = new Order();
        orderParam.setOrderId(order.getOrderId());
        orderParam.setDvyId(deliveryOrderParam.getDvyId());
        orderParam.setDvyFlowId(deliveryOrderParam.getDvyFlowId());
        orderParam.setDvyTime(new Date());
        orderParam.setStatus(OrderStatus.CONSIGNMENT.value());
        orderParam.setUserId(order.getUserId());

        orderService.delivery(orderParam);
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderNumber(deliveryOrderParam.getOrderNumber());
        for (OrderItem orderItem : orderItems) {
            productService.removeProductCacheByProdId(orderItem.getProdId());
            skuService.removeSkuCacheBySkuId(orderItem.getSkuId(), orderItem.getProdId());
        }
        return ServerResponseEntity.success();
    }

    @GetMapping("/waitingConsignmentExcel")
    @PreAuthorize("@pms.hasPermission('order:order:waitingConsignmentExcel')")
    @Operation(summary = "order-waitingConsignmentExcel", description = "匯出待出貨訂單表")
    public void waitingConsignmentExcel(Order order, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime, String consignmentName,
                                        String consignmentMobile, String consignmentAddr, HttpServletResponse response) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        order.setShopId(shopId);
        order.setStatus(OrderStatus.PADYED.value());
        List<Order> orders = orderService.listOrdersDetailByOrder(order, startTime, endTime);

        // 通過工具創建writer
        ExcelWriter writer = ExcelUtil.getBigWriter();
        Sheet sheet = writer.getSheet();
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 20 * 256);
        sheet.setColumnWidth(8, 20 * 256);
        sheet.setColumnWidth(9, 20 * 256);

        // 待出貨
        String[] header = {"訂單號", "收件人", "手機", "收貨地址", "商品名稱", "數量", "寄件人姓名", "寄件人手機號", "寄件地址", "備註"};
        writer.merge(header.length - 1, "出貨訊息整理");
        writer.writeRow(Arrays.asList(header));

        int row = 1;
        for (Order dbOrder : orders) {
            UserAddrOrder addr = dbOrder.getUserAddrOrder();
            String addrInfo = addr.getProvince() + addr.getCity() + addr.getArea() + addr.getAddr();
            List<OrderItem> orderItems = dbOrder.getOrderItems();
            row++;
            for (OrderItem orderItem : orderItems) {
                // 從第0列開始
                int col = 0;
                writer.writeCellValue(col++, row, dbOrder.getOrderNumber());
                writer.writeCellValue(col++, row, addr.getReceiver());
                writer.writeCellValue(col++, row, addr.getMobile());
                writer.writeCellValue(col++, row, addrInfo);
                writer.writeCellValue(col++, row, orderItem.getProdName());
                writer.writeCellValue(col++, row, consignmentName);
                writer.writeCellValue(col++, row, consignmentMobile);
                writer.writeCellValue(col++, row, consignmentAddr);
                writer.writeCellValue(col++, row, dbOrder.getRemarks());
            }
        }
        writeExcel(response, writer);
    }

    @GetMapping("/soldExcel")
    @PreAuthorize("@pms.hasPermission('order:order:soldExcel')")
    @Operation(summary = "order-soldExcel", description = "匯出已銷售訂單")
    public void soldExcel(Order order, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime, HttpServletResponse response) {
        Long shopId = SecurityUtils.getSysUser().getShopId();
        order.setShopId(shopId);
        order.setIsPayed(1);
        List<Order> orders = orderService.listOrdersDetailByOrder(order, startTime, endTime);

        ExcelWriter writer = ExcelUtil.getBigWriter();
        String[] header = {"訂單編號", "下單時間", "收件人", "手機", "收貨地址", "商品名稱", "數量", "訂單應付金額", "訂單運費", "訂單實際付款金額"};
        Sheet sheet = writer.getSheet();
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 60 * 256);
        sheet.setColumnWidth(5, 60 * 256);

        writer.merge(header.length - 1, "銷售訊息整理");
        writer.writeRow(Arrays.asList(header));

        int row = 1;
        for (Order dbOrder : orders) {
            UserAddrOrder addr = dbOrder.getUserAddrOrder();
            String addrInfo = addr.getProvince() + addr.getCity() + addr.getArea() + addr.getAddr();
            List<OrderItem> orderItems = dbOrder.getOrderItems();
            int firstRow = row + 1;
            int lastRow = row + orderItems.size();
            int col = -1;
            // 訂單編號
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, dbOrder.getOrderNumber());
            // 下單時間
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, dbOrder.getCreateTime());
            // 收件人
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, addr.getReceiver());
            // 手機
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, addr.getMobile());
            // 收貨地址
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, addrInfo);
            int prodNameCol = ++col;
            int prodCountCol = ++col;
            for (OrderItem orderItem : orderItems) {
                row++;
                // 商品名稱
                writer.writeCellValue(prodNameCol, row, orderItem.getProdName());
                // 數量
                writer.writeCellValue(prodCountCol, row, orderItem.getProdCount());
            }
            // 訂單應付金額
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, dbOrder.getTotal());
            // 訂單運費
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, dbOrder.getFreightAmount());
            // 訂單實際付款金額
            mergeIfNeed(writer, firstRow, lastRow, ++col, col, dbOrder.getActualTotal());
        }
        writeExcel(response, writer);
    }

    private void mergeIfNeed(ExcelWriter writer, int firstRow, int lastRow, int firstColumn, int lastColumn, Object content) {
        if (content instanceof Date) {
            content = DateUtil.format((Date) content, DatePattern.NORM_DATETIME_PATTERN);
        }
        if (lastRow > firstRow || lastColumn > firstColumn) {
            writer.merge(firstRow, lastRow, firstColumn, lastColumn, content, false);
        } else {
            writer.writeCellValue(firstColumn, firstRow, content);
        }
    }


    private void writeExcel(HttpServletResponse response, ExcelWriter writer) {
        // response -> HttpServletResponse Object
        response.setContentType("\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // test.xls 為彈出下載對話框的文件名，不能為中文
        response.setHeader("Content-Disposition", "attachment; filename=order.xlsx");
        ServletOutputStream sos = null;
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            writer.flush(outputStream);
            outputStream.flush();
        } catch (IORuntimeException | IOException e) {
            log.error("匯出excel異常：", e);
        } finally {
            IoUtil.close(writer);
        }
    }

}
