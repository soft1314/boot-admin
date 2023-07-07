package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.bautil.dto.ResultDTO;

public interface MapstructGenerator {
    ResultDTO generateFile(String tableName,boolean overwrite,TgProject tgProject) throws Exception;
    ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception;
}
