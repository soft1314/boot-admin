package com.qiyuan.base2048.pojo.vo.eltable;

import lombok.Data;

@Data
public class ProjectVO extends ElTableBaseVO{
    /**
     *  组织ID
     */
    private String groupId;
    /**
     *  项目ID
     */
    private String artifactId;
    /**
     *  项目包名
     */
    private String packageName;
    /**
     *  项目名称
     */
    private String name;
    /**
     *  是否缺省项目
     */
    private String defaultProj;
    /**
     *  是否启用swagger
     */
    private String enableSwagger;
    /**
     *  项目JAVA文件路径
     */
    private String javaOutputDir;
    /**
     *  项目Mapper XML文件路径
     */
    private String xmlOutputDir;
    /**
     *  作者
     */
    private String author;
}
