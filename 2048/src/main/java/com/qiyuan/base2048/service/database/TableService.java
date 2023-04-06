package com.qiyuan.base2048.service.database;

import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;

public interface TableService {
    VTablesMaster getOneByTableName(String tableName) throws Exception;
}
