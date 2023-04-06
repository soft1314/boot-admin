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
* 系统用户基础表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-12-15 13:27:48
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_USER")
@ApiModel(description = "系统用户基础表实体类")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  用户姓
     */
    @TableField("FIRST_NAME")
    private String firstName;
    /**
     *  用户名
     */
    @TableField("LAST_NAME")
    private String lastName;
    /**
     *  登录名
     */
    @TableField("LOGON_NAME")
    private String logonName;
    /**
     *  用户密码
     */
    @TableField("PWD")
    private String pwd;
    /**
     *  邮箱
     */
    @TableField("EMAIL")
    private String email;
    /**
     *  手机号
     */
    @TableField("MOBILE")
    private String mobile;
    /**
     *  机构GUID
     */
    @TableField("ORG_GUID")
    private String orgGuid;
    /**
     *  办公电话
     */
    @TableField("OFFICE_TEL")
    private String officeTel;
    /**
     *  标记orgid隶属的类型：01-单位/部门，02-处室
     */
    @TableField("ORG_TYPE")
    private String orgType;
    /**
     *  密码加密算法GUID
     */
    @TableField("PWD_TYPE")
    private String pwdType;
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
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT)
    private Integer version;
    /**
     *  人员ID
     */
    @TableField("EMP_GUID")
    private String empGuid;
    /**
     *  区划编码
     */
    @TableField("ADM_DIV_CODE")
    private String admDivCode;
    /**
     *  区划名称
     */
    @TableField("ADM_DIV_NAME")
    private String admDivName;
    /**
     *  机构编码
     */
    @TableField("ORG_CODE")
    private String orgCode;
    /**
     *  机构编码
     */
    @TableField("ORG_NAME")
    private String orgName;
    /**
     *  区划权限类型;ADM_DIV_PERMISSION
     */
    @TableField("DIV_RANGE_TYPE")
    private String divRangeType;
    /**
     *  机构权限类型;ORG_PERMISSION
     */
    @TableField("ORG_RANGE_TYPE")
    private String orgRangeType;
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
     *  启用状态;ENABLED
     */
    @TableField("ENABLED")
    private String enabled;
    /**
     *  删除状态;DELETED
     */
    @TableField("DELETED")
    private String deleted;
}