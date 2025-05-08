package com.demo.mall.service;

import com.demo.mall.bean.app.dto.ProductItemDto;
import com.demo.mall.bean.model.UserAddr;

public interface TransportManagerService {


    double calculateTransnfee(ProductItemDto shopCartItem, UserAddr userAddr);
}
