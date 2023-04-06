package com.qiyuan.gateway4196.cache.impl;

import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.gateway4196.cache.ResourceCacheService;
import com.qiyuan.gateway4196.feign.FeignBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-27 10:16
 **/
@Slf4j
@Service
public class ResourceCacheServiceImpl implements ResourceCacheService {
    @Resource
    private FeignBaseService feignBaseService;
    @Resource
    private ThreadPoolTaskExecutor threadPool;

    @Cacheable(value = "Base:Resource", key = "'Uri:'+#uri", unless = "#result==null")
    @Override
    public MenuDTO fetchMenuDtoByUri(String uri) throws Exception {
        log.info("refresh cache base:resource:uri={}----------更新缓存",uri);
        /** WebFlux异步调用，同步会报错  **/
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<MenuDTO> future = threadPool.submit(() -> (MenuDTO) feignBaseService.fetchMenuDtoByUri(uri));
//        Future future = executorService.submit(() -> (MenuDTO) feignBaseService.fetchMenuDtoByUri(uri));
        MenuDTO menuDTO = (MenuDTO)future.get();
//        executorService.shutdown();
        return menuDTO;
    }
    @Cacheable(value = "Base:ResourceRoleList", key = "'Uri:'+#uri", unless = "#result==null")
    @Override
    public List<String> fetchRoleListByUri(String uri) throws Exception {
        log.info("refresh cache base:ResourceRoleList:uri={}----------更新缓存",uri);
        /** WebFlux异步调用，同步会报错  **/
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<String>> future = threadPool.submit(() -> (List<String>) feignBaseService.fetchRoleListByUri(uri));
//        Future future = executorService.submit(() -> (List<String>) feignBaseService.fetchRoleListByUri(uri));
        List<String> functionRoles = (List<String>)future.get();
//        executorService.shutdown();
        return functionRoles;
    }
}
