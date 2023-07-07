package com.qiyuan.base2048.service.common;

import com.qiyuan.bautil.dto.ResultDTO;

public interface AttachmentGetter {
    ResultDTO fetchAttachmentList(String tbName, String tbStyle, String tbGuid) throws Exception;
}
