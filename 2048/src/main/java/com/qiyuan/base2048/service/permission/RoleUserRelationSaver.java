package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.save.RoleUserVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface RoleUserRelationSaver {
    ResultDTO grantOneUser(RoleUserVO roleUserVO) throws Exception;
    ResultDTO revokeOneUser(RoleUserVO roleUserVO) throws Exception;
}
