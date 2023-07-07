package com.qiyuan.base2048.service.common;

import javax.servlet.http.HttpServletResponse;

public interface MinioDownloadService {
    void fetchMinioFile(String guid, HttpServletResponse response) throws Exception;
}
