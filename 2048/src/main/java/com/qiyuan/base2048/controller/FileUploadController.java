package com.qiyuan.base2048.controller;

import com.qiyuan.base2048.service.common.MinioUploadService;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/file")
@Slf4j
public class FileUploadController {
    @Resource
    private MinioUploadService minioUploadService;

    @PostMapping("/upload-with-data")
    public ResultDTO uploadWithData(@RequestParam("file") MultipartFile multipartFile,@RequestParam("mainTableName") String mainTableName,@RequestParam("mainStyle") String mainStyle,@RequestParam("mainGuid") String mainGuid) throws Exception {
        return minioUploadService.saveAttach(multipartFile,  mainGuid,mainTableName,mainStyle);
    }
}
