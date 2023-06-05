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
* 访问日志表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_LOG_REQUEST")
@ApiModel(description = "访问日志表实体类")
public class TbLogRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  IP地址
     */
    @TableField("OPER_IP")
    private String operIp;
    /**
     *  访问点
     */
    @TableField("URI")
    private String uri;
    /**
     *  异常消息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;
    /**
     *  访问方法
     */
    @TableField("METHOD")
    private String method;
    /**
     *  请求方式
     */
    @TableField("REQUEST_METHOD")
    private String requestMethod;
    /**
     *  消耗时间
     */
    @TableField("COST_TIME")
    private String costTime;
    /**
     *  业务类型;BUSINESS_TYPE
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;
    /**
     *  操作模块
     */
    @TableField("BUSINESS_MODULE")
    private String businessModule;
    /**
     *  操作人员类别;OPERATOR_TYPE
     */
    @TableField("OPERATOR_TYPE")
    private String operatorType;
    /**
     *  请求参数
     */
    @TableField("OPER_PARAM")
    private String operParam;
    /**
     *  响应结果
     */
    @TableField("JSON_RESULT")
    private String jsonResult;
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