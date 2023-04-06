package com.qiyuan.base2048.service.permission.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleResource;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleUser;
import com.qiyuan.base2048.service.permission.RoleDataSaver;
import com.qiyuan.base2048.service.transaction.LocalTransaction;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-07 23:09
 **/
@Service
@Slf4j
public class RoleDataSaverImpl implements RoleDataSaver {
    @Resource
    private LocalTransaction localTransaction;

    @Override
    public boolean saveResourcePower(String resourceGuid, List<String> roleGuids) throws Exception {
        List<TrRoleResource> trResourceRoleList = new ArrayList<>();
        TrRoleResource trResourceRole;
        for (String roleGuid : roleGuids) {
            trResourceRole = new TrRoleResource();
            trResourceRole.setRoleGuid(roleGuid);
            trResourceRole.setResourceGuid(resourceGuid);
            trResourceRole.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
            trResourceRole.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
            trResourceRoleList.add(trResourceRole);
        }
        return localTransaction.saveResourceRoles(resourceGuid,trResourceRoleList);
    }

    @Override
    public boolean saveUserPower(String userGuid, List<String> roleGuids) throws Exception {
        List<TrRoleUser> trUserRoleList = new ArrayList<>();
        TrRoleUser trUserRole;
        for (String roleGuid : roleGuids) {
            trUserRole = new TrRoleUser();
            trUserRole.setRoleGuid(roleGuid);
            trUserRole.setUserGuid(userGuid);
            trUserRole.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
            trUserRole.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
            trUserRoleList.add(trUserRole);
        }
        return localTransaction.saveUserRoles(userGuid,trUserRoleList);
    }

}
