package com.qiyuan.base2048.service.permission;


import com.qiyuan.bautil.dto.TransferDataDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDataGetter {
    /**
     * 获取资源的权限穿梭框数据
     * @param resourceGuid
     * @return
     * @throws Exception
     */
    TransferDataDTO getResourceRoleTransferData(String resourceGuid) throws Exception;
    /**
     * 使用USERGUID查询该用户所有角色,穿梭框数据格式
     * @param userGuid
     * @return
     * @throws Exception
     */

    TransferDataDTO getUserRoleTransferData(String userGuid) throws Exception;
    /**
     * 使用资源URI查询该资源所有角色
     * @param uri
     * @return
     * @throws Exception
     */
    List<String> selectRoleListByResourceUri(@Param("uri") String uri) throws Exception;
    /**
     * 使用USERGUID查询该用户所有角色
     * @param userGuid
     * @return
     * @throws Exception
     */
    List<String> getUserRoleList(String userGuid) throws Exception;
}
