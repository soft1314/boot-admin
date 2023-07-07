package com.qiyuan.base2048.service.generator;

import com.qiyuan.bautil.dto.ResultDTO;

public interface TableChecker {
    ResultDTO checkTable(String tableName) throws Exception;
}
