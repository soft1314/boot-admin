package com.qiyuan.base2048.service.oa;

import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifySender {
    ResultDTO send(String notifyGuid) throws Exception;
}
