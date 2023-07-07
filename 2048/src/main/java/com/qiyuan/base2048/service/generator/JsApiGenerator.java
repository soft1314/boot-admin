package com.qiyuan.base2048.service.generator;

import com.qiyuan.bautil.dto.ResultDTO;

public interface JsApiGenerator {
    ResultDTO generateCode(String tableName,String path,String author) throws Exception;
    ResultDTO generateFile(String tableName,String path,String author,boolean overwrite) throws Exception;
}
