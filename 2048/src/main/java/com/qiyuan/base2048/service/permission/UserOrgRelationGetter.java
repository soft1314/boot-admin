package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.query.DataPermissionQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface UserOrgRelationGetter {
    ResultDTO fetchGrantedPage(DataPermissionQueryVO queryByGuidDTO) throws Exception;
    ResultDTO fetchGrantablePage(DataPermissionQueryVO queryByGuidDTO) throws Exception;
}
