package com.qiyuan.base2048.service.generator;

import com.qiyuan.bautil.dto.ResultDTO;

public interface MybatisPlusGenerator {
    ResultDTO generateEntitySource(String tableName,String packageUrl,String path,String author) throws Exception;
    ResultDTO generateEntity(String tableName,String packageUrl,String path,String author,boolean overwrite) throws Exception;

    ResultDTO generateMapper(String tableName,String packageUrl,String entityPackageUrl,String mapperClassFilePath,String mapperXmlFilePath,String author) throws Exception;
    ResultDTO generateMapperSource(String tableName,String packageUrl,String entityPackageUrl,String mapperClassFilePath,String mapperXmlFilePath,String author) throws Exception;

    ResultDTO generateService(String tableName,String packageUrl,String entityPackageUrl,String mapperPackageUrl,String path,String author) throws Exception;
    ResultDTO generateServiceSource(String tableName,String packageUrl,String entityPackageUrl,String mapperPackageUrl,String path,String author) throws Exception;

    ResultDTO generateControllerSource(String tableName,String packageUrl,String path,String author) throws Exception;
    ResultDTO generateController(String tableName,String packageUrl,String path,String author) throws Exception;

    ResultDTO generateVo(String tableName,String packageUrl,String path,String author,boolean overwrite) throws Exception;
    ResultDTO generateVoSource(String tableName,String packageUrl,String path,String author) throws Exception;
}
