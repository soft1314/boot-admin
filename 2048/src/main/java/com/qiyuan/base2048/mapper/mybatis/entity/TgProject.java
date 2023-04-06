package com.qiyuan.base2048.mapper.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 代码生成功能项目定义表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-12-15 13:27:57
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TG_PROJECT")
@ApiModel(description = "代码生成功能项目定义表实体类")
public class TgProject implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  组织ID
     */
    @TableField("GROUP_ID")
    private String groupId;
    /**
     *  项目ID
     */
    @TableField("ARTIFACT_ID")
    private String artifactId;
    /**
     *  项目名称
     */
    @TableField("NAME")
    private String name;
    /**
     *  项目包名
     */
    @TableField("PACKAGE_PROJ")
    private String packageProj;
    /**
     *  MP-ENTITY包名
     */
    @TableField("PACKAGE_ENTITY")
    private String packageEntity;
    /**
     *  MP-MAPPER包名
     */
    @TableField("PACKAGE_MAPPER")
    private String packageMapper;
    /**
     *  MP-SERVICE包名
     */
    @TableField("PACKAGE_SERVICE")
    private String packageService;
    /**
     *  MP-CONTROLLER包名
     */
    @TableField("PACKAGE_CONTROLLER")
    private String packageController;
    /**
     *  VO包名
     */
    @TableField("PACKAGE_VO")
    private String packageVo;
    /**
     *  项目JAVA文件路径
     */
    @TableField("JAVA_OUTPUT_DIR")
    private String javaOutputDir;
    /**
     *  项目Mapper XML文件路径
     */
    @TableField("XML_OUTPUT_DIR")
    private String xmlOutputDir;
    /**
     *  VUE文件路径
     */
    @TableField("VUE_DIR")
    private String vueDir;
    /**
     *  API文件路径
     */
    @TableField("API_JS_DIR")
    private String apiJsDir;
    /**
     *  代码类型定义JS路径
     */
    @TableField("DICTYPE_JS_DIR")
    private String dictypeJsDir;
    /**
     *  是否缺省项目
     */
    @TableField("DEFAULT_PROJ")
    private String defaultProj;
    /**
     *  是否启用swagger
     */
    @TableField("ENABLE_SWAGGER")
    private String enableSwagger;
    /**
     *  作者
     */
    @TableField("AUTHOR")
    private String author;
    /**
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT)
    private Integer version;
    /**
     *  记录创建时间
     */
    @TableField(value = "CREATE_TIME" ,fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *  记录创建者
     */
    @TableField(value = "CREATE_BY" ,fill = FieldFill.INSERT)
    private String createBy;
    /**
     *  记录最后修改时间
     */
    @TableField(value = "MODIFY_TIME" ,fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    /**
     *  记录最后修改者
     */
    @TableField(value = "MODIFY_BY" ,fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;
    /**
     *  时间戳
     */
    @TableField("DATESTAMP")
    private Date datestamp;
    /**
     *  租户ID
     */
    @TableField("TENANT_ID_")
    private String tenantId_;
    /**
     *  MAPSTRUCT包名
     */
    @TableField("PACKAGE_MAPSTRUCT")
    private String packageMapstruct;
    /**
     *  CHANGE_LOG路径
     */
    @TableField("CHANGE_LOG_DIR")
    private String changeLogDir;
    /**
     *  剧情SERVICE包名
     */
    @TableField("PACKAGE_SERVICE_SC")
    private String packageServiceSc;
    /**
     *  剧情CONTROLLER包名
     */
    @TableField("PACKAGE_CONTROLLER_SC")
    private String packageControllerSc;
    /**
     *  删除状态;DELETED
     */
    @TableField("DELETED")
    private String deleted;
    /**
     *  启用状态;ENABLED
     */
    @TableField("ENABLED")
    private String enabled;
    /**
     *  备注
     */
    @TableField("REMARKS")
    private String remarks;

    /** 前端VIEWS路径 **/
    @TableField("VIEWS_DIR")
    private String viewsDir;
}