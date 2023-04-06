package com.qiyuan.base2048.service.generator;

public interface TxtFileSummaryGetter {
    String fromDb(String fullFileName) throws Exception;
    String fromIo(String fullFileName) throws Exception;
}
