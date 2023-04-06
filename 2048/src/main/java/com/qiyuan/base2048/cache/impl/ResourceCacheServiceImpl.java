package com.qiyuan.base2048.cache.impl;

import com.qiyuan.base2048.cache.ResourceCacheService;
import com.qiyuan.base2048.service.permission.ResourceDataGetter;

import com.qiyuan.bautil.dto.MenuDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-27 10:16
 **/
@Slf4j
@Service
public class ResourceCacheServiceImpl implements ResourceCacheService {
    @Resource
    private ResourceDataGetter resourceDataGetter;

    @Cacheable(value = "Base:Resource", key = "'Uri:'+#uri", unless = "#result==null")
    @Override
    public MenuDTO getMenuDtoByUri(String uri) throws Exception {
        log.info("refresh cache base:resource:uri={}----------更新缓存",uri);
        MenuDTO menuDTO = resourceDataGetter.getMenuDtoByUri(uri);
        return menuDTO;
    }

    @CacheEvict(value= "Base:Resource" ,allEntries= true )
    @Override
    public void reload() throws Exception{
        log.info("clear cache Base:Resource allEntries----------清空缓存");
    }
}
