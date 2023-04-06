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
* 系统资源（功能、菜单）基础表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-28 21:56:03
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_RESOURCE")
@ApiModel(description = "系统资源（功能、菜单）基础表实体类")
public class TbResource implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  统一资源标志符
     */
    @TableField("URI")
    private String uri;
    /**
     *  资源分类 1菜单,2功能点
     */
    @TableField("RESOURCE_TYPE")
    private String resourceType;
    /**
     *  资源访问控制类型01自由访问02登录访问03指定角色访问
     */
    @TableField("ACCESS_CONTROL_STYLE")
    private String accessControlStyle;
    /**
     *  资源名称
     */
    @TableField("NAME")
    private String name;
    /**
     *  记录创建时间
     */
    @TableField(value = "CREATE_TIME" ,fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *  记录最后修改时间
     */
    @TableField(value = "MODIFY_TIME" ,fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
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
     *  租户ID
     */
    @TableField("TENANT_ID_")
    private String tenantId_;
    /**
     *  备注
     */
    @TableField("REMARKS")
    private String remarks;
    /**
     *  顺序号
     */
    @TableField("SORT")
    private BigDecimal sort;
    /**
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT)
    private Integer version;
    /**
     *  父级GUID
     */
    @TableField("PARENT_GUID")
    private String parentGuid;
    /**
     *  记录创建者
     */
    @TableField(value = "CREATE_BY" ,fill = FieldFill.INSERT)
    private String createBy;
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
     *  是否末级;YESNO
     */
    @TableField("LEAF")
    private String leaf;
}