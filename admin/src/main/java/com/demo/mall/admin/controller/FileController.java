package com.demo.mall.admin.controller;

import com.demo.mall.bean.enums.UploadType;
import com.demo.mall.common.bean.Qiniu;
import com.demo.mall.common.response.ServerResponseEntity;
import com.demo.mall.common.utils.ImgUploadUtil;
import com.demo.mall.service.AttachFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/admin/file")
@Tag(name = "admin-file", description = "文件上傳")
public class FileController {
    @Autowired
    private AttachFileService attachFileService;

    @Autowired
    private Qiniu qiniu;

    @Autowired
    private ImgUploadUtil ImgUploadUtil;
    @Autowired
    private ImgUploadUtil imgUploadUtil;

    @PostMapping("/upload/element")
    public ServerResponseEntity<String> uploadElement(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ServerResponseEntity.success();
        }
        String fileName = attachFileService.uploadFile(file);
        return ServerResponseEntity.success(fileName);
    }

    @PostMapping("/upload/tinymceEditor")
    public ServerResponseEntity<String> uploadTinynceEditorImages(@RequestParam("editorFile") MultipartFile editorFile) throws IOException {
        String fileName = attachFileService.uploadFile(editorFile);
        String data = "";
        if (Objects.equals(imgUploadUtil.getUploadType(), UploadType.LOCAL.value())) {
            data = imgUploadUtil.getUploadPath() + fileName;
        } else if (Objects.equals(imgUploadUtil.getUploadType(), UploadType.QINIU.value())) {
            data = qiniu.getResourcesUrl() + fileName;
        }
        return ServerResponseEntity.success(data);
    }
}
