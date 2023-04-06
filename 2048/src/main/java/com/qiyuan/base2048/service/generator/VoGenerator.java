package com.qiyuan.base2048.service.generator;

import com.qiyuan.bautil.dto.ResultDTO;

public interface VoGenerator {
    ResultDTO generateFile(String tableName,String packageUrl,String path,String author,boolean overwrite) throws Exception;
    ResultDTO generateCode(String tableName,String packageUrl,String path,String author) throws Exception;
}
