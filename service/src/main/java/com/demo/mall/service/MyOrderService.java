package com.demo.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.app.dto.MyOrderDto;
import com.demo.mall.bean.model.Order;
import com.demo.mall.common.utils.PageParam;

public interface MyOrderService extends IService<Order> {

    // 通過用戶id和訂單狀態分頁獲取訂單訊息
    IPage<MyOrderDto> pageMyOrderByUserIdAndStatus(PageParam<MyOrderDto> page, String useId, Integer status);
}
