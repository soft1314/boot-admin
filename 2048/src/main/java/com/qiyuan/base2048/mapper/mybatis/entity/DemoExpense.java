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
* 示例表（业务工作流）报销实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:06:58
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("DEMO_EXPENSE")
@ApiModel(description = "示例表（业务工作流）报销实体类")
public class DemoExpense implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  标题
     */
    @TableField("TITLE")
    private String title;
    /**
     *  报销金额
     */
    @TableField("MONEY")
    private BigDecimal money;
    /**
     *  情况说明
     */
    @TableField("NOTE")
    private String note;
    /**
     *  业务日期
     */
    @TableField("BIZ_DATE")
    private Date bizDate;
    /**
     *  报销人
     */
    @TableField("EMP_NAME")
    private String empName;
    /**
     *  职称;TECH_TITLE;LAZY
     */
    @TableField("TECH_TITLE")
    private String techTitle;
    /**
     *  员工级别;EMP_LEVEL
     */
    @TableField("EMP_LEVEL")
    private String empLevel;
    /**
     *  性别;SEX
     */
    @TableField("SEX")
    private String sex;
    /**
     *  流转状态;FLOW_STATUS
     */
    @TableField("FLOW_STATUS")
    private String flowStatus;
    /**
     *  流程定义ID
     */
    @TableField("PROCESS_DEFINITION_ID")
    private String processDefinitionId;
    /**
     *  流程实例ID
     */
    @TableField("PROCESS_INSTANCE_ID")
    private String processInstanceId;
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