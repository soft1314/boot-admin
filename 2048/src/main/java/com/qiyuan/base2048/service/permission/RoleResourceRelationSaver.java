package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.save.RoleResourceVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface RoleResourceRelationSaver {
    ResultDTO grantOneResource(RoleResourceVO roleResourceVO) throws Exception;
    ResultDTO revokeOneResource(RoleResourceVO roleResourceVO) throws Exception;
}
