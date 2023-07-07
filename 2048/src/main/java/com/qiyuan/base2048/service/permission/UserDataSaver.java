package com.qiyuan.base2048.service.permission;

import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.save.UserDataPermissionTypeVO;
import com.qiyuan.base2048.pojo.vo.save.UserEmpVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface UserDataSaver {
    boolean updateUser(TbUser tbUser) throws Exception;
    boolean insertUser(TbUser tbUser) throws Exception;
    ResultDTO insertOrUpdateUser(TbUser tbUser) throws Exception;
    boolean deleteUserByGuid(String guid) throws Exception;
    /**
     * 保存系统用户的隶属人员信息
     * @return
     * @throws Exception
     */
    ResultDTO saveUserEmp(UserEmpVO userEmpVO) throws Exception;
    ResultDTO saveUserAdmDivDataPermissionType(UserDataPermissionTypeVO userDataPermissionTypeVO) throws Exception;
    ResultDTO saveUserOrgDataPermissionType(UserDataPermissionTypeVO userDataPermissionTypeVO) throws Exception;
}
