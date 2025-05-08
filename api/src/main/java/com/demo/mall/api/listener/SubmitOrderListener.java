package com.demo.mall.api.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.demo.mall.bean.app.dto.ShopCartItemDiscountDto;
import com.demo.mall.bean.app.dto.ShopCartItemDto;
import com.demo.mall.bean.app.dto.ShopCartOrderDto;
import com.demo.mall.bean.app.dto.ShopCartOrderMergerDto;
import com.demo.mall.bean.enums.OrderStatus;
import com.demo.mall.bean.event.SubmitOrderEvent;
import com.demo.mall.bean.model.*;
import com.demo.mall.bean.order.SubmitOrderOrder;
import com.demo.mall.common.constants.Constant;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.dao.*;
import com.demo.mall.security.api.util.SecurityUtils;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.SkuService;
import com.demo.mall.service.UserAddrOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("defaultSubmitOrderListener")
public class SubmitOrderListener {
    @Autowired
    private UserAddrOrderService userAddrOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettlementMapper orderSettlementMapper;

    @Autowired
    private BasketMapper basketMapper;

    @EventListener(SubmitOrderListener.class)
    @Order(SubmitOrderOrder.DEFAULT)
    public void defaultSubmitOrderListener(SubmitOrderEvent event) {
        Date now = new Date();
        String userId = SecurityUtils.getUser().getUserId();

        ShopCartOrderMergerDto mergerOrder = event.getMergerOrder();
        // 訂單商品參數
        List<ShopCartOrderDto> shopCartOrders = mergerOrder.getShopCartOrders();

        List<Long> basketIds = new ArrayList<>();
        // 商品skuId 為 key 須更新的 sku 為 value 的map
        Map<Long, Sku> skuStocksMap = new HashMap<>(16);
        // 商品productId 為 key 需要更新的product 為 value 的 map
        Map<Long, Product> prodStocksMap = new HashMap<>(16);

        // 將訂單地址保存至數據庫
        UserAddrOrder userAddrOrder = BeanUtil.copyProperties(mergerOrder.getUserAddr(), UserAddrOrder.class);
        if (userAddrOrder == null) {
            throw new ShopException("請填寫收貨地址");
        }
        userAddrOrder.setUserId(userId);
        userAddrOrder.setCreateTime(now);
        userAddrOrderService.save(userAddrOrder);

        // 訂單地址id
        Long addrOrderId = userAddrOrder.getAddrOrderId();

        // 每個商店生成一個訂單
        for (ShopCartOrderDto shopCartOrder : shopCartOrders) {
            createOrder(event, now, userId, basketIds, skuStocksMap, prodStocksMap, addrOrderId, shopCartOrder);
        }

        // 刪除購物車的商品訊息
        if (!basketIds.isEmpty()) {
            basketMapper.deleteShopCartItemsByBasketIds(userId, basketIds);
        }

        // 更新sku庫存
        skuStocksMap.forEach((k, sku) -> {
            if (skuMapper.updateStocks(sku) == 0) {
                skuService.removeSkuCacheBySkuId(k, sku.getProdId());
                throw new ShopException("商品:[" + sku.getProdName() + "]庫存不足");
            }
        });

        // 更新商品庫存
        prodStocksMap.forEach((prodId, prod) -> {
            if (productMapper.updateStocks(prod) == 0) {
                productService.removeProductCacheByProdId(prodId);
                throw new ShopException("商品：[" + prod.getProdName() + "]庫存不足");
            }
        });


    }

    private void createOrder(SubmitOrderEvent event, Date now, String userId, List<Long> basketIds, Map<Long, Sku> skuStocksMap, Map<Long, Product> prodStocksMap, Long addrOrderId, ShopCartOrderDto shopCartOrderDto) {
        // 使用snowflake生成訂單號
        String orderNumber = String.valueOf(snowflake.nextId());
        shopCartOrderDto.setOrderNumber(orderNumber);

        Long shopId = shopCartOrderDto.getShopId();

        // 訂單商品名稱
        StringBuilder orderProdName = new StringBuilder(100);
        List<OrderItem> orderItems = new ArrayList<>();

        List<ShopCartItemDiscountDto> shopCartItemDiscounts = shopCartOrderDto.getShopCartItemDiscounts();
        for (ShopCartItemDiscountDto shopCartItemDiscount : shopCartItemDiscounts) {
            List<ShopCartItemDto> shopCartItems = shopCartItemDiscount.getShopCartItems();
            for (ShopCartItemDto shopCartItem : shopCartItems) {
                Sku sku = checkAndGetSku(shopCartItem.getSkuId(), shopCartItem, skuStocksMap);
                Product product = checkAndGetProd(shopCartItem.getProdId(), shopCartItem, prodStocksMap);

                OrderItem orderItem = getOrderItem(now, userId, orderNumber, shopId, orderProdName, shopCartItem, sku, product);
                orderItems.add(orderItem);
                if (shopCartItem.getBasketId() != null && shopCartItem.getBasketId() != 0) {
                    basketIds.add(shopCartItem.getBasketId());
                }
            }
        }

        orderProdName.subSequence(0, Math.min(orderProdName.length() - 1, 100));
        if (orderProdName.lastIndexOf(Constant.COMMA) == orderProdName.length() - 1) {
            orderProdName.deleteCharAt(orderProdName.length() - 1);
        }

        // 訂單訊息
        com.demo.mall.bean.model.Order order = getOrder(now, userId, addrOrderId, shopCartOrderDto, orderNumber, shopId, orderProdName, orderItems);
        OrderSettlement orderSettlement = new OrderSettlement();
        orderSettlement.setUserId(userId);
        orderSettlement.setIsClearing(0);
        orderSettlement.setCreateTime(now);
        orderSettlement.setOrderNumber(orderNumber);
        orderSettlement.setPayAmount(order.getActualTotal());
        orderSettlement.setPayStatus(0);
        orderSettlement.setVersion(0);
        orderSettlementMapper.insert(orderSettlement);
    }

    private com.demo.mall.bean.model.Order getOrder(Date now, String userId, Long addrOrderId, ShopCartOrderDto shopCartOrderDto, String orderNumber, Long shopId, StringBuilder orderProdName, List<OrderItem> orderItems) {
        com.demo.mall.bean.model.Order order = new com.demo.mall.bean.model.Order();
        order.setShopId(shopId);
        order.setOrderNumber(orderNumber);
        order.setProdName(orderProdName.toString());
        order.setUserId(userId);
        order.setTotal(shopCartOrderDto.getTotal());
        order.setStatus(OrderStatus.UNPAY.value());
        order.setUpdateTime(now);
        order.setCreateTime(now);
        order.setIsPayed(0);
        order.setDeleteStatus(0);
        order.setProductNums(shopCartOrderDto.getTotalCount());
        order.setAddrOrderId(addrOrderId);
        order.setReduceAmount(Arith.sub(Arith.add(shopCartOrderDto.getTotal(), shopCartOrderDto.getTransfee()), shopCartOrderDto.getActualTotal()));
        order.setFreightAmount(shopCartOrderDto.getTransfee());
        order.setRemarks(shopCartOrderDto.getRemarks());
        order.setOrderItems(orderItems);
        return order;
    }

    private OrderItem getOrderItem(Date now, String userId, String orderNumber, Long shopId, StringBuilder orderProdName, ShopCartItemDto shopCartItem, Sku sku, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setShopId(shopId);
        orderItem.setOrderNumber(orderNumber);
        orderItem.setProdId(sku.getProdId());
        orderItem.setSkuId(sku.getSkuId());
        orderItem.setSkuName(sku.getSkuName());
        orderItem.setProdCount(shopCartItem.getProdCount());
        orderItem.setProdName(sku.getProdName());
        orderItem.setPic(StrUtil.isBlank(sku.getPic()) ? product.getPic() : sku.getPic());
        orderItem.setPrice(shopCartItem.getPrice());
        orderItem.setUserId(userId);
        orderItem.setProductTotalAmount(shopCartItem.getProductTotalAmount());
        orderItem.setRecTime(now);
        orderItem.setCommSts(0);
        orderItem.setBasketDate(shopCartItem.getBasketDate());
        orderProdName.append(orderItem.getProdName()).append(",");
        orderItem.setDistributionCardNo(shopCartItem.getDistributionCardNo());
        return orderItem;


    }

    private Product checkAndGetProd(Long prodId, ShopCartItemDto shopCartItem, Map<Long, Product> prodStocksMap) {
        Product product = productService.getProductByProdId(prodId);
        if (product == null) {
            throw new ShopException("購物車含有無法辨識的商品");
        }
        if (product.getStatus() != 1) {
            throw new ShopException("商品:[" + product.getProdName() + "]已下架");
        }
        Product mapProduct = prodStocksMap.get(prodId);
        if (mapProduct == null) {
            mapProduct = new Product();
            mapProduct.setTotalStocks(0);
            mapProduct.setProdName(product.getProdName());
            mapProduct.setProdId(prodId);
        }
        if (product.getTotalStocks() != -1) {
            mapProduct.setTotalStocks(mapProduct.getTotalStocks() + shopCartItem.getProdCount());
            prodStocksMap.put(prodId, mapProduct);
        }

        // -1 為無限庫存
        if (product.getTotalStocks() != -1 && mapProduct.getTotalStocks() > product.getTotalStocks()) {
            throw new ShopException("商品:[" + product.getProdName() + "]庫存不足");
        }
        return product;
    }

    private Sku checkAndGetSku(Long skuId, ShopCartItemDto shopCartItem, Map<Long, Sku> skuStocksMap) {
        // 獲取sku訊息
        Sku sku = skuService.getSkuBySkuId(skuId);
        if (sku == null) {
            throw new ShopException("購物車包含無法識別商品");
        }
        if (sku.getStatus() != 1) {
            throw new ShopException("商品:[" + sku.getProdName() + "]已下架");
        }
        // -1 為無限庫存
        if (sku.getStocks() != -1 && shopCartItem.getProdCount() > sku.getStocks()) {
            throw new ShopException("商品:[" + sku.getProdName() + "]庫存不足");
        }

        if (sku.getStocks() != -1) {
            Sku mapSku = new Sku();
            mapSku.setProdId(sku.getProdId());
            mapSku.setSkuId(skuId);
            mapSku.setStocks(shopCartItem.getProdCount());
            mapSku.setProdName(sku.getProdName());
            skuStocksMap.put(skuId, mapSku);
        }
        return sku;
    }
}
