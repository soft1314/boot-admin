package com.qiyuan.base2048.service.database;

import com.qiyuan.base2048.pojo.vo.vuecomponent.TableColVO;

import java.util.List;

public interface ColumnService {
    /**
     * 取指定表列
     * @param tableName
     * @return
     * @throws Exception
     */
    List<TableColVO> getTableCols(String tableName) throws Exception;
}
