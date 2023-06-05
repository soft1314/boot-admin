package com.qiyuan.gateway4096.service.impl;

import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.bautil.enums.ResourceAccessControlStyleEnum;
import com.qiyuan.gateway4096.service.MenuPermissionChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-26 10:51
 **/
@Service
@Slf4j
public class MenuPermissionCheckerImpl implements MenuPermissionChecker {
    @Override
    public boolean isFreeAccess(MenuDTO menuDTO) {
        if(menuDTO == null){
            return false;
        }
        if(ResourceAccessControlStyleEnum.FREE.getValue().equals(menuDTO.getAccessControlStyle())){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAllRoleAccess(MenuDTO menuDTO) {
        if(menuDTO == null){
            return false;
        }
        if(ResourceAccessControlStyleEnum.ALL_ROLE.getValue().equals(menuDTO.getAccessControlStyle())){
            return true;
        }
        return false;
    }
}
