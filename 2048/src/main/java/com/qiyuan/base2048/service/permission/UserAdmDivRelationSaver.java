package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.save.UserDivVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface UserAdmDivRelationSaver {
    ResultDTO grantOneDiv(UserDivVO userDivVO) throws Exception;
    ResultDTO revokeOneDiv(UserDivVO userDivVO) throws Exception;
}
