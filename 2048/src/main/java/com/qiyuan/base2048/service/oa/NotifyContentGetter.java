package com.qiyuan.base2048.service.oa;

import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyContentGetter {
    ResultDTO loadContent(String notifyGuid) throws Exception;
}
