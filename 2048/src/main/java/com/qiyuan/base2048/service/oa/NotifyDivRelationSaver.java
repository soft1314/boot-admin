package com.qiyuan.base2048.service.oa;

import com.qiyuan.base2048.pojo.vo.oa.NotifyAdmDivRelationshipVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyDivRelationSaver {
    /**
     * 保存某个通知通告需要通知到哪些区划
     * @param notifyAdmDivRelationshipVO
     * @return
     * @throws Exception
     */
    ResultDTO saveTrNotifyAdmDiv(NotifyAdmDivRelationshipVO notifyAdmDivRelationshipVO) throws Exception;
}
