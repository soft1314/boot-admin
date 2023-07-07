package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.save.UserOrgVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface UserOrgRelationSaver {
    ResultDTO grantOneOrg(UserOrgVO userDivVO) throws Exception;
    ResultDTO revokeOneOrg(UserOrgVO userDivVO) throws Exception;
}
