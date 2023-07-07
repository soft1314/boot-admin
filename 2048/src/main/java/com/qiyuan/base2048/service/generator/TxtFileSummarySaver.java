package com.qiyuan.base2048.service.generator;

public interface TxtFileSummarySaver {
    int save(String fullFileName,String summary) throws Exception;
    int backup(String fullFileName,String bakFileName) throws Exception;
}
