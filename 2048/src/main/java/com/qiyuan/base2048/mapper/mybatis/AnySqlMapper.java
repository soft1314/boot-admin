package com.qiyuan.base2048.mapper.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface AnySqlMapper {
    List<LinkedHashMap<String, Object>> superManagerSelect(@Param("sql") String sql);
    int superManagerInsert(@Param("sql") String sql);
    int superManagerUpdate(@Param("sql") String sql);
    int superManagerDelete(@Param("sql") String sql);
    void createNewTableAndInsertData(@Param("sql") String sql);
}
