package com.demo.mall.api.listener;

import com.demo.mall.bean.app.dto.ShopCartDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.dto.ShopCartOrderDto;
import com.demo.mall.bean.app.param.OrderParam;
import com.demo.mall.bean.event.ConfirmOrderEvent;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.bean.model.UserAddr;
import com.demo.mall.bean.order.ConfirmOrderOrder;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("defaultConfirmOrderListener")
public class ConfirmOrderListener {

    @Autowired
    private UserAddrService userAddrService;

    @Autowired
    private TransportManagerService transportManagerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @EventListener(ConfirmOrderEvent.class)
    @Order(ConfirmOrderOrder.DEFAULT)
    public void defaultConfirmOrderEvent(ConfirmOrderEvent event) {
        ShopCartOrderDto shopCartDto = event.getShopCartOrderDto();

        OrderParam orderParam = event.getOrderParam();

        String userId = SecurityUtils.getUser().getUserId();

        // 訂單地址訊息
        UserAddr userAddr = userAddrService.getUserAddrByUserId(orderParam.getAddrId(), userId);
        double total = 0.0;
        int totalCount = 0;
        double transfee = 0.0;

        for (ShopCartItemDto shopCartItem : event.getShopCartItems()) {
            // 獲取商品資訊
            Product product = productService.getProductByProdId(shopCartItem.getProdId());
            // 獲取sku訊息
            Sku sku = skuService.getSkuBySkuId(shopCartItem.getSkuId());
            if (product == null || sku == null) {
                throw new ShopException("購物車中包含無法識別的商品");
            }
            if (product.getStatus() != 1 || sku.getStatus() != 1) {
                throw new ShopException("商品[" + sku.getProdName() + "]已下架");
            }
            totalCount += shopCartItem.getProdCount();
            total = Arith.add(shopCartItem.getProductTotalAmount(), total);
            // 用戶地址若為空，則表示該用戶未設置任何地址相關訊息
            if (userAddr != null) {
                // 將每個商品的運費相加
                transfee = Arith.add(transportManagerService.calculateTransnfee(shopCartItem, userAddr), transfee);
            }
            shopCartItem.setActualTotal(shopCartItem.getProductTotalAmount());
            shopCartDto.setActualTotal(Arith.add(total, transfee));
            shopCartDto.setTotal(total);
            shopCartDto.setTotalCount(totalCount);
            shopCartDto.setTransfee(transfee);
        }
    }
}
