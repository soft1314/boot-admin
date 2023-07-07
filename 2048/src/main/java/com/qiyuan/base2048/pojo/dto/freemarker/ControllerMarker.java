package com.qiyuan.base2048.pojo.dto.freemarker;

import lombok.Data;

@Data
public class ControllerMarker {
    /** 表描述 **/
    private String tableComment;
    /** 包全名 **/
    private String packageUrl;
    /** 作者 **/
    private String author;
    /** 实体类名 首字母大写驼峰 **/
    private String entityName;
    /** VO类包全名 **/
    private String voPackageUrl;
    /** Service包全名 **/
    private String servicePackageUrl;
    /** 表名小写 **/
    private String tableNameLowercase;
    /** 表名小写 减号连接**/
    private String tableNameLowercaseMinusSign;
}
