package com.qiyuan.base2048.service.transaction;

import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleResource;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleUser;
import java.util.List;

public interface LocalTransaction {
    boolean saveResourceRoles(String resourceGuid, List<TrRoleResource> trResourceRoles) throws Exception;
    boolean saveUserRoles(String userGuid, List<TrRoleUser> trUserRoles) throws Exception;
}
