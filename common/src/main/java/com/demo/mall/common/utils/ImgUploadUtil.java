package com.demo.mall.common.utils;

import cn.hutool.core.util.StrUtil;
import com.demo.mall.common.bean.ImgUpload;
import com.demo.mall.common.exception.ShopException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
@Tag(name = "file upload and delete tool", description = "文件上傳與刪除工具")
public class ImgUploadUtil {

    @Autowired
    private ImgUpload imgUpload;

    public Integer getUploadType() {
        Integer uploadType = imgUpload.getUploadType();
        if (Objects.isNull(uploadType)) {
            throw new ShopException("請配置圖片儲存方式");
        }
        return uploadType;
    }

    public String getUploadPath() {
        String imagePath = imgUpload.getImagePath();
        if (Objects.isNull(imagePath) || StrUtil.isBlank(imagePath)) {
            throw new ShopException("請配置圖片儲存路徑");
        }
        return imagePath;
    }

    public String getResourceUrl() {
        String resourceUrl = imgUpload.getResourceUrl();
        if (Objects.isNull(resourceUrl) || StrUtil.isBlank(resourceUrl)) {
            throw new ShopException("請配置圖片路徑");
        }
        return resourceUrl;
    }

    public String upload(MultipartFile img, String fileName) {
        String filePath = imgUpload.getImagePath();
        File file = new File(filePath);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                throw new ShopException("創建目錄：" + filePath + "失敗");
            }
        }
        try {
            img.transferTo(file);
        } catch (IOException e) {
            throw new ShopException("圖片上傳失敗");
        }
        return fileName;
    }

    public void delete(String fileName) {
        String filePath = imgUpload.getImagePath();
        File file = new File(filePath + fileName);
        file.deleteOnExit();
    }
}
