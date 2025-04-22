package com.demo.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.mall.bean.model.AttachFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachFileService extends IService<AttachFile> {

    // 上傳文件到本地
    String uploadFile(MultipartFile file) throws IOException;
}
