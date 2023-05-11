package com.qiyuan.base2048.service.oa;

import com.qiyuan.base2048.pojo.vo.save.NotifyOrgVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyOrgRelationSaver {
    ResultDTO grantOneOrg(NotifyOrgVO notifyOrgVO) throws Exception;
    ResultDTO revokeOneOrg(NotifyOrgVO notifyOrgVO) throws Exception;
}
