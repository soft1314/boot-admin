package com.qiyuan.base2048.service.oa;

import com.qiyuan.base2048.pojo.vo.save.NotifyEmpVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyEmpRelationSaver {
    ResultDTO grantOneEmp(NotifyEmpVO notifyEmpVO) throws Exception;
    ResultDTO revokeOneEmp(NotifyEmpVO notifyEmpVO) throws Exception;
}
