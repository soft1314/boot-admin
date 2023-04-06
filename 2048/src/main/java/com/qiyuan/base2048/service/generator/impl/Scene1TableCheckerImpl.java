package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.generator.BasicTableChecker;
import com.qiyuan.base2048.service.generator.TableChecker;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("Scene1TableCheckerImpl")
public class Scene1TableCheckerImpl implements TableChecker {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Resource
    private BasicTableChecker basicTableChecker;

    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO checkTable(String tableName) throws Exception {
        List<UserTableColumnResultDTO> userTableColumnResultDTOList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName);
        return basicTableChecker.checkCols(userTableColumnResultDTOList);
    }
}
