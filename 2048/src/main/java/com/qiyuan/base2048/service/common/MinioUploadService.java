package com.qiyuan.base2048.service.common;

import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MinioUploadService {
    /**
     * 存储附件信息，并返回附件dto
     * @param multipartFile
     * @return
     * @throws Exception
     */
    ResultDTO saveAttach(MultipartFile multipartFile, String pGuid,String pTbname,String pStyle) throws Exception;
}
