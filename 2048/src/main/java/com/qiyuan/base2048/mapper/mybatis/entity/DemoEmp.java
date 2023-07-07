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
* 示例表（基本CRUD）人员表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("DEMO_EMP")
@ApiModel(description = "示例表（基本CRUD）人员表实体类")
public class DemoEmp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  姓名
     */
    @TableField("NAME")
    private String name;
    /**
     *  性别;SEX
     */
    @TableField("SEX")
    private String sex;
    /**
     *  出生年月
     */
    @TableField("BIRTHDAY")
    private Date birthday;
    /**
     *  职称;TECH_TITLE;LAZY
     */
    @TableField("TECH_TITLE")
    private String techTitle;
    /**
     *  薪水
     */
    @TableField("SALARY")
    private String salary;
    /**
     *  情况说明
     */
    @TableField("NOTE")
    private String note;
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