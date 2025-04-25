package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.Transport;

public interface TransportService extends IService<Transport> {

    // 根據模板id獲取運費模板和運費項
    Transport getTransportAndAllItems(Long id);

    // 插入運費模板和運費項
    void insertTransportAndTransfee(Transport transport);

    // 根據運費模板和運費項目修改
    void updateTransportAndTransfee(Transport transport);

    // 根據id列表刪除運費模板和運費項
    void deleteTransportAndTransfeeAndTranscity(Long[] ids);

    // 刪除緩存
    void removeTransportAndAllItemsCache(Long transportId);
}
