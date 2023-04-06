package com.qiyuan.gateway4196.service;


import com.qiyuan.bautil.dto.MenuDTO;

public interface MenuPermissionChecker {
    /** 是否自由使用 **/
    boolean isFreeAccess(MenuDTO menuDTO);
    /** 是否登录后自由使用 **/
    boolean isAllRoleAccess(MenuDTO menuDTO);
}
