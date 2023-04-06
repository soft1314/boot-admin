package com.qiyuan.base2048.cache;


import com.qiyuan.bautil.dto.MenuDTO;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-27 10:14
 **/
public interface ResourceCacheService {
    void reload() throws Exception;
    MenuDTO getMenuDtoByUri(String uri) throws Exception;
}
