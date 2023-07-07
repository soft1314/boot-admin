package com.qiyuan.base2048.mapper.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserTableColumnMasterGetter extends BaseMapper {
//    @Select("select utc.column_name,utc.data_type,utc.data_length,utc.data_scale,utc.char_length,utc.nullable,'' as data_default,ucc.comments,utc.table_name,case when utc.COLUMN_NAME in (select col.column_name from user_constraints con,user_cons_columns col where con.constraint_name = col.constraint_name and con.constraint_type = 'P' and col.table_name = #{tableName}) then 'Y' else 'N' end as primary_key from user_tab_columns utc,user_col_comments ucc where utc.table_name = ucc.table_name and utc.column_name = ucc.column_name and utc.table_name = #{tableName} order by column_id")
    @Select("select column_name,data_type,data_length,data_scale,char_length,nullable,'' as data_default,comments,table_name,primary_key from v_columns_master where table_schema = #{schemaName} and table_name = #{tableName} order by column_id")
    List<UserTableColumnResultDTO> selectUserTableColumn(@Param("schemaName")String schemaName,@Param("tableName")String tableName);
}
