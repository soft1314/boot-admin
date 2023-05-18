package com.qiyuan.base2048.service.oa;

import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyContentSaver {
    /**
     * 保存正文
     * @param notifyGuid
     * @param content
     * @return
     * @throws Exception
     */
    ResultDTO saveContent(String notifyGuid,String content) throws Exception;
}
