package com.qiyuan.base2048.pojo.dto.mybatis;

import lombok.Data;

@Data
public class MybatisPlusDTO {
    /** 包全名 **/
    private String packageUrl;
    /** 实体类名 **/
    private String entityName;
    /** 表名描述 **/
    private String tableComment;
    /** 表名 **/
    private String tableName;
    /** 作者 **/
    private String author;
}
