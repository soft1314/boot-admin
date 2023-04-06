package com.qiyuan.base2048.service.permission;


import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.bautil.dto.ResultDTO;

public interface ResourceDataSaver {
    boolean updateResource(TbResource tbResource) throws Exception;
    boolean insertResource(TbResource tbResource) throws Exception;
    boolean insertOrUpdateResource(TbResource tbResource) throws Exception;
    ResultDTO deleteByGuid(String guid) throws Exception;
}
