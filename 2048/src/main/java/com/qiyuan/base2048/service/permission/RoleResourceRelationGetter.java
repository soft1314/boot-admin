package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.query.ResourcePageQueryParamVO;
import com.qiyuan.base2048.pojo.vo.query.RolePermissionQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface RoleResourceRelationGetter {
    ResultDTO fetchGrantedPage(RolePermissionQueryVO rolePermissionQueryVO) throws Exception;
    ResultDTO fetchGrantablePage(RolePermissionQueryVO rolePermissionQueryVO) throws Exception;
}
