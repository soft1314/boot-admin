package com.qiyuan.log6144.mapper.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 一般日志表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_LOG_GENERAL")
@ApiModel(description = "一般日志表实体类")
public class TbLogGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;

    @TableField("TRACE_ID")
    private String traceId;
    @TableField("SPAN_ID")
    private String spanId;
    @TableField("PARENT_ID")
    private String parentId;
    @TableField("APP_NAME")
    private String appName;
    @TableField("HOST_NAME")
    private String hostName;
    /**
     *  日志级别
     */
    @TableField("LOG_LEVEL")
    private String logLevel;
    /**
     *  类名
     */
    @TableField("CLASS_NAME")
    private String className;
    /**
     *  行号
     */
    @TableField("LINE_NO")
    private String lineNo;
    /**
     *  操作人
     */
    @TableField("OPERATOR")
    private String operator;
    /**
     *  日志内容
     */
    @TableField("LOG_CONTENT")
    private String logContent;
    /**
     *  记录创建者
     */
    @TableField(value = "CREATE_BY" ,fill = FieldFill.INSERT)
    private String createBy;
    /**
     *  记录创建时间
     */
    @TableField(value = "CREATE_TIME" ,fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *  记录最后修改者
     */
    @TableField(value = "MODIFY_BY" ,fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;
    /**
     *  记录最后修改时间
     */
    @TableField(value = "MODIFY_TIME" ,fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    /**
     *  时间戳
     */
    @TableField("DATESTAMP")
    private Date datestamp;
    /**
     *  启用状态;ENABLED
     */
    @TableField("ENABLED")
    private String enabled;
    /**
     *  删除状态;DELETED
     */
    @TableField("DELETED")
    private String deleted;
    /**
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT)
    private Integer version;
    /**
     *  备注
     */
    @TableField("REMARKS")
    private String remarks;
    /**
     *  租户ID
     */
    @TableField("TENANT_ID_")
    private String tenantId_;
}