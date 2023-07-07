package com.qiyuan.base2048.service.transaction.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleResource;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleUser;
import com.qiyuan.base2048.service.mybatis.ITrRoleResourceService;
import com.qiyuan.base2048.service.mybatis.ITrRoleUserService;
import com.qiyuan.base2048.service.transaction.LocalTransaction;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-07 23:18
 **/
@Service
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class LocalTransactionImpl implements LocalTransaction {
    @Autowired
    private ITrRoleResourceService resourceRoleService;
    @Autowired
    private ITrRoleUserService userRoleService;

    @Override
    public boolean saveResourceRoles(String resourceGuid, List<TrRoleResource> trResourceRoles) throws Exception {
        /** 逻辑删除原数据 **/
        UpdateWrapper<TrRoleResource> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("resource_guid",resourceGuid).set("deleted", IsDeletedEnum.DELETED.getStringValue());
        resourceRoleService.update(updateWrapper);
        /** 插入新数据 **/
        resourceRoleService.saveBatch(trResourceRoles);
        return true;
    }

    @Override
    public boolean saveUserRoles(String userGuid, List<TrRoleUser> trUserRoles) throws Exception {
        /** 逻辑删除原数据 **/
        UpdateWrapper<TrRoleUser> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_guid",userGuid).set("deleted", IsDeletedEnum.DELETED.getStringValue());

        userRoleService.update(updateWrapper);
        /** 插入新数据 **/
        userRoleService.saveBatch(trUserRoles);

        return true;
    }
}
