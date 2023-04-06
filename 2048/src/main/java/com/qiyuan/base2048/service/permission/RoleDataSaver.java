package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.pojo.vo.save.UserEmpVO;
import com.qiyuan.bautil.dto.ResultDTO;

import java.util.List;

public interface RoleDataSaver {
    /**
     * 保存系统资源的角色列表权限
     * @param resourceGuid
     * @param roleGuids
     * @return
     * @throws Exception
     */
    boolean saveResourcePower(String resourceGuid, List<String> roleGuids) throws Exception;
    /**
     * 保存系统用户的角色列表权限
     * @param userGuid
     * @param roleGuids
     * @return
     * @throws Exception
     */
    boolean saveUserPower(String userGuid, List<String> roleGuids) throws Exception;

}
