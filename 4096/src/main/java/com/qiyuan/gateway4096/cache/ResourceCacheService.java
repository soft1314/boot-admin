package com.qiyuan.gateway4096.cache;

import com.qiyuan.bautil.dto.MenuDTO;

import java.util.List;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-27 10:14
 **/
public interface ResourceCacheService {
    /**
     * 根据URI取菜单DTO
     * @param uri
     * @return
     * @throws Exception
     */
    MenuDTO fetchMenuDtoByUri(String uri) throws Exception;

    /**
     * 根据URI取可操作资源的角色GUID列表
     * @param uri
     * @return
     * @throws Exception
     */
    List<String> fetchRoleListByUri(String uri) throws Exception;
}
