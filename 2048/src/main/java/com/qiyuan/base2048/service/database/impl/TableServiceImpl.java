package com.qiyuan.base2048.service.database.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.VTablesMasterMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.service.database.TableService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TableServiceImpl implements TableService {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Resource
    private VTablesMasterMapper vTablesMasterMapper;

    @Override
    public VTablesMaster getOneByTableName(String tableName) throws Exception {
        QueryWrapper<VTablesMaster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TABLE_SCHEMA",tableSchema);
        queryWrapper.eq("TABLENAME",tableName.toUpperCase());
        return vTablesMasterMapper.selectOne(queryWrapper);
    }
}
