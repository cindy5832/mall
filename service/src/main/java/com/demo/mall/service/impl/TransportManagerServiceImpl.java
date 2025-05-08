package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.demo.mall.bean.app.dto.ProductItemDto;
import com.demo.mall.bean.enums.TransportChargeType;
import com.demo.mall.bean.model.*;
import com.demo.mall.common.utils.Arith;
import com.demo.mall.common.utils.Json;
import com.demo.mall.service.ProductService;
import com.demo.mall.service.SkuService;
import com.demo.mall.service.TransportManagerService;
import com.demo.mall.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransportManagerServiceImpl implements TransportManagerService {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private TransportService transportService;

    @Override
    public double calculateTransnfee(ProductItemDto productItem, UserAddr userAddr) {
        Product product = productService.getProductByProdId(productItem.getProdId());
        Long cityId = userAddr.getCityId();

        Product.DeliveryModeVO deliveryModeVO = Json.parseObject(product.getDeliveryMode(), Product.DeliveryModeVO.class);

        // 無商店配送方式
        if (!deliveryModeVO.getHasShopDelivery()) {
            return 0.0;
        }
        if (product.getDeliveryTemplateId() == null) {
            return 0.0;
        }

        // 找出商品個運費項
        Transport transport = transportService.getTransportAndAllItems(product.getDeliveryTemplateId());
        // 商家把模板刪除
        if (transport == null) {
            return 0.0;
        }

        Sku sku = skuService.getSkuBySkuId(productItem.getSkuId());

        // 計算運費件數
        Double piece = getPiece(productItem, transport, sku);

        // 若有免運條件
        if (transport.getHasFreeCondition() == 1) {
            // 獲取所有免運條件
            List<TransfeeFree> transfeeFrees = transport.getTransfeeFrees();
            for (TransfeeFree transfeeFree : transfeeFrees) {
                List<Area> freeCityList = transfeeFree.getFreeCityList();
                for (Area freeCity : freeCityList) {
                    if (!Objects.equals(freeCity.getAreaId(), cityId)) {
                        continue;
                    }
                    // 免運方式 0 滿x件/重量/體積 免運 1 滿額免運 2 滿x件/重量/體積且金額 免運
                    boolean isFree = (transfeeFree.getFreeType() == 0 && piece >= transfeeFree.getAmount()) ||
                            (transfeeFree.getFreeType() == 1 && productItem.getProductTotalAmount() >= transfeeFree.getAmount()) ||
                            (transfeeFree.getFreeType() == 2 && piece >= transfeeFree.getPiece() && productItem.getProductTotalAmount() >= transfeeFree.getAmount());
                    if (isFree) {
                        return 0.0;
                    }
                }
            }
        }
        // 訂單運費項目
        Transfee transfee = null;
        List<Transfee> transfees = transport.getTransfees();
        for (Transfee dbtransfee : transfees) {
            if (transfee == null && CollectionUtil.isEmpty(dbtransfee.getCityList())) {
                transfee = dbtransfee;
            }
            // 若在運費模板中的城市找到該商品運，則將該商品由默認運費設置改為該城市運費
            for (Area area : dbtransfee.getCityList()) {
                if (area.getAreaId().equals(cityId)) {
                    transfee = dbtransfee;
                    break;
                }
            }
            // 若在運費模板中的城市找到該商品的運費，則退出整個循環
            if (transfee != null && CollectionUtil.isNotEmpty(transfee.getCityList())) {
                break;
            }
        }
        // 若無法獲得任何運費相關訊息
        if (transfee == null) {
            return 0.0;
        }

        // 產品運費
        Double fee = transfee.getFirstFee();
        // 若件數大於首件數量，則開始計算超出費用
        if (piece > transfee.getFirstFee()) {
            // 續件數量
            Double prodContinuousPiece = Arith.sub(piece, transfee.getFirstPiece());
            // 續件數量的倍數，向上取整
            Integer mulNumber = (int) Math.ceil(Arith.mul(prodContinuousPiece, transfee.getContinuousPiece()));
            // 續件數量運費
            Double continuousFee = Arith.mul(mulNumber, transfee.getContinuousFee());
            fee = Arith.add(fee, continuousFee);
        }
        return fee;
    }

    private Double getPiece(ProductItemDto productItem, Transport transport, Sku sku) {
        Double pieces = 0.0;
        if (Objects.equals(TransportChargeType.COUNT.value(), transport.getChargeType())) {
            // 按件計算運費
            pieces = Double.valueOf(productItem.getProdCount());
        } else if (Objects.equals(TransportChargeType.WEIGHT.value(), transport.getChargeType())) {
            // 按重量計算運費
            double weight = sku.getWeight() == null ? 0 : sku.getWeight();
            pieces = Arith.mul(weight, productItem.getProdCount());
        } else if (Objects.equals(TransportChargeType.VOLUME.value(), transport.getChargeType())) {
            // 按體積計算運費
            double volume = sku.getVolume() == null ? 0 : sku.getVolume();
            pieces = Arith.mul(volume, productItem.getProdCount());
        }
        return pieces;
    }
}
