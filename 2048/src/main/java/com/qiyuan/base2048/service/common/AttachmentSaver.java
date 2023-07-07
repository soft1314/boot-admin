package com.qiyuan.base2048.service.common;

import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.bautil.dto.ResultDTO;

import java.math.BigDecimal;

public interface AttachmentSaver {
    TbAttachment genTbAttachment(String mainGuid, String mainStyle, String mainTableName, String bucketName, String fileName, String originalName, String contentType, String fileSize);
    int saveTbAttachment(TbAttachment tbAttachment) throws Exception;
    ResultDTO removeAttachment(String guid,String fileName) throws Exception;
}
