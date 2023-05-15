package com.qiyuan.base2048.service.oa;

import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyContentSaver {
    ResultDTO saveContent(String notifyGuid,String content) throws Exception;
}
