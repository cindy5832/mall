package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.model.*;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.dao.TranscityMapper;
import com.demo.mall.dao.TransfeeMapper;
import com.demo.mall.dao.TransportMapper;
import com.demo.mall.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements TransportService {

    @Autowired
    private TransportMapper transportMapper;

    @Autowired
    private TransfeeMapper transfeeMapper;

    @Autowired
    private TranscityMapper transcityMapper;

    @Override
    @Cacheable(cacheNames = "TransportAndAllItems", key = "#transportId")
    public Transport getTransportAndAllItems(Long transportId) {
        Transport transport = transportMapper.getTransportAndTransfeeAndTranscity(transportId);
        if (transport == null) {
            return null;
        }
        List<TransfeeFree> transfeeFrees = transportMapper.getTransfeeFreeAndTranscityFreeByTransportId(transportId);
        transport.setTransfeeFrees(transfeeFrees);
        return transport;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTransportAndTransfee(Transport transport) {
        transportMapper.insert(transport);
        // 插入所有運費項和城市
        insertTransfeeAndTranscity(transport);
        // 插入所有的指定免運條件和城市
        if (transport.getHasFreeCondition() == 1) {
            insertTransfeeFreeAndTranscityFree(transport);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "TransportAndAllItems", key = "#transport.transportId")
    public void updateTransportAndTransfee(Transport transport) {
        Transport dbTransport = getTransportAndAllItems(transport.getTransportId());

        // 刪除所有的運費項目
        transfeeMapper.deleteByTrnasportId(transport.getTransportId());
        // 刪除所有指定免運項目
        transfeeMapper.deleteTransfeeFreeByTransportId(transport.getTransportId());

        List<Long> transfeeIds = dbTransport.getTransfees()
                .stream().map(Transfee::getTransfeeId).toList();
        List<Long> transfeeFreeIds = dbTransport.getTransfeeFrees()
                .stream().map(TransfeeFree::getTransfeeFreeId).toList();
        // 刪除所有運費想項包含城市
        transcityMapper.deleteBatchByTransfeeIds(transfeeIds);

        if (CollectionUtil.isNotEmpty(transfeeFreeIds)) {
            // 刪除所有指定免運項包含城市
            transcityMapper.deleteBatchByTransfeeFreeIds(transfeeFreeIds);
        }
        // 更新運費模板
        transportMapper.updateById(transport);
        // 插入所有的運費項和城市
        insertTransfeeAndTranscity(transport);
        // 插入所有的指定免運項和城市
        if (transport.getHasFreeCondition() == 1) {
            insertTransfeeFreeAndTranscityFree(transport);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransportAndTransfeeAndTranscity(Long[] ids) {
        for (Long id : ids) {
            Transport dbTransport = getTransportAndAllItems(id);
            List<Long> transfeeIds = dbTransport.getTransfees()
                    .stream().map(Transfee::getTransfeeId).toList();
            // 刪除所有運費項所包含城市
            transcityMapper.deleteBatchByTransfeeIds(transfeeIds);
            // 刪除所有的運費項目
            transfeeMapper.deleteByTrnasportId(id);
        }
        // 刪除運費模板
        transportMapper.deleteTransports(ids);
    }

    @Override
    @CacheEvict(cacheNames = "TransportAndAllItems", key = "#transportId")
    public void removeTransportAndAllItemsCache(Long transportId) {

    }

    private void insertTransfeeFreeAndTranscityFree(Transport transport) {
        List<TransfeeFree> transfeeFrees = transport.getTransfeeFrees();
        for (TransfeeFree transfeeFree : transfeeFrees) {
            transfeeFree.setTransportId(transport.getTransportId());
        }

        // 批量插入指定免運項目並返回指定免運條件 id
        transfeeMapper.insertTransfeeFrees(transfeeFrees);

        List<TranscityFree> transcityFrees = new ArrayList<>();
        for (TransfeeFree transfeeFree : transfeeFrees) {
            List<Area> cityList = transfeeFree.getFreeCityList();
            if (CollectionUtil.isEmpty(cityList)) {
                throw new ShopException("請選擇指定免運城市");
            }
            // 當地址不為空時
            for (Area area : cityList) {
                TranscityFree transcityParam = new TranscityFree();
                transcityParam.setTransfeeFreeId(transfeeFree.getTransfeeFreeId());
                transcityParam.setFreeCityId(area.getAreaId());
                transcityFrees.add(transcityParam);
            }
        }
        // 批量插入指定免運條件項中的城市
        if (CollectionUtil.isNotEmpty(transcityFrees)) {
            transcityMapper.insertTranscityFrees(transcityFrees);
        }
    }

    private void insertTransfeeAndTranscity(Transport transport) {
        Long transportId = transport.getTransportId();
        List<Transfee> transfees = transport.getTransfees();
        for (Transfee transfee : transfees) {
            transfee.setTransportId(transportId);
        }
        // 批量插入運費項 並返回運費項id
        transfeeMapper.insertTransfees(transfees);
        List<Transcity> transcities = new ArrayList<>();
        for (Transfee transfee : transfees) {
            List<Area> cityList = transfee.getCityList();
            if (CollectionUtil.isEmpty(cityList)) {
                continue;
            }
            // 當地址不為空
            for (Area area : cityList) {
                Transcity transcityParam = new Transcity();
                transcityParam.setTransfeeId(transfee.getTransfeeId());
                transcityParam.setCityId(area.getAreaId());
                transcities.add(transcityParam);
            }
        }

        // 批量插入運費項中的城市
        if (CollectionUtil.isNotEmpty(transcities)) {
            transcityMapper.insertTranscities(transcities);
        }
    }
}
