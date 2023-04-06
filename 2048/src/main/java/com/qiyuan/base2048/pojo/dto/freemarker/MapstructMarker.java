package com.qiyuan.base2048.pojo.dto.freemarker;

import lombok.Data;

@Data
public class MapstructMarker {
    /** 表描述 **/
    private String tableComment;
    /** 包全名 **/
    private String packageUrl;
    /** 作者 **/
    private String author;
    /** 实体类名 首字母大写驼峰 **/
    private String entityName;
    /** 实体类全名 **/
    private String entityFullName;
    /** 数据VO类名 **/
    private String dataVoName;
    /** 数据VO类全名 **/
    private String dataVoFullName;

}
