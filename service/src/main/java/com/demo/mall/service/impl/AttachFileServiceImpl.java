package com.demo.mall.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.enums.UploadType;
import com.demo.mall.bean.model.AttachFile;
import com.demo.mall.common.bean.Qiniu;
import com.demo.mall.common.utils.ImgUploadUtil;
import com.demo.mall.common.utils.Json;
import com.demo.mall.dao.AttachFileMapper;
import com.demo.mall.service.AttachFileService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class AttachFileServiceImpl extends ServiceImpl<AttachFileMapper, AttachFile> implements AttachFileService {

    public final static String NORM_MONTH_PATTERN = "yyyy/MM/";

    @Autowired
    private ImgUploadUtil imgUploadUtil;

    @Autowired
    private AttachFileMapper attachFileMapper;

    @Autowired
    private Auth auth;
    @Autowired
    private Qiniu qiniu;
    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadFile(MultipartFile file) throws IOException {
        String extName = FileUtil.extName(file.getOriginalFilename());
        String fileName = DateUtil.format(new Date(), NORM_MONTH_PATTERN) + IdUtil.simpleUUID() + "." + extName;
        AttachFile attachFile = new AttachFile();
        attachFile.setFilePath(fileName);
        attachFile.setFileSize(file.getBytes().length);
        attachFile.setFileType(extName);
        attachFile.setUploadTime(new Date());
        if (Objects.equals(imgUploadUtil.getUploadType(), UploadType.LOCAL.value())) {
            // 1 本地文件上傳
            attachFileMapper.insert(attachFile);
            return imgUploadUtil.upload(file, fileName);
        } else {
            // 2 七牛雲文件上傳
            String uptoken = auth.uploadToken(qiniu.getBucket(), fileName);
            Response response = uploadManager.put(file.getBytes(), fileName, uptoken);
            Json.parseObject(response.bodyString(), DefaultPutRet.class);
            return fileName;
        }
    }

    public void deleteFile(String fileName) {
        attachFileMapper.delete(
                new LambdaQueryWrapper<AttachFile>()
                        .eq(AttachFile::getFilePath, fileName));
        try {
            if (Objects.equals(imgUploadUtil.getUploadType(), UploadType.LOCAL.value())) {
                imgUploadUtil.delete(fileName);
            } else if (Objects.equals(imgUploadUtil.getUploadType(), UploadType.QINIU.value())) {
                bucketManager.delete(qiniu.getBucket(), fileName);
            }
        }catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }
}
