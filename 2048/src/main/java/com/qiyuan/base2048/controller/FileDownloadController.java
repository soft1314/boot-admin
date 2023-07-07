package com.qiyuan.base2048.controller;

import com.qiyuan.base2048.service.common.MinioDownloadService;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RequestMapping("/api/system/auth/file")
@RestController
@ResponseBodyInController
@Slf4j
public class FileDownloadController {
    @Resource
    private MinioDownloadService minioDownloadService;

    @GetMapping(value = "/attachment")
    @ApiOperation("获取附件流")
    public void fetchFile(@RequestParam("guid") String guid, HttpServletResponse response) throws Exception {
        minioDownloadService.fetchMinioFile(guid,response);
    }
}
