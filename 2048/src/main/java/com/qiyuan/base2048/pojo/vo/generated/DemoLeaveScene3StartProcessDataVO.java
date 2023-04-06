package com.qiyuan.base2048.pojo.vo.generated;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本工作流）请假 工作流流程启动VO
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:13:12
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "示例表（基本工作流）请假流程启动VO类")
public class DemoLeaveScene3StartProcessDataVO implements Serializable {

    private static final long serialVersionUID = 1L;
        /**
        * 主键
        */
    private String guid;
            /**
            *  标题
            */
        private String title;
            /**
            *  请假天数
            */
        private BigDecimal days;
            /**
            *  请假类型;LEAVE_TYPE
            */
        private String leaveType;
            /**
            *  情况说明
            */
        private String note;
            /**
            *  请假人
            */
        private String empName;
            /**
            *  请假日期
            */
        private Date bizDate;
            /**
            *  离岗时间
            */
        private Date startTime;
            /**
            *  返岗时间
            */
        private Date finishTime;
            /**
            *  职称;TECH_TITLE;LAZY
            */
        private String techTitle;
            /**
            *  职务级别;EMP_LEVEL
            */
        private String empLevel;
            /**
            *  性别;SEX
            */
        private String sex;
            /**
            *  流转状态;FLOW_STATUS
            */
        private String flowStatus;
            /**
            *  记录创建者
            */
        private String createBy;
            /**
            *  记录创建时间
            */
        private Date createTime;
            /**
            *  记录最后修改者
            */
        private String modifyBy;
            /**
            *  记录最后修改时间
            */
        private Date modifyTime;
            /**
            *  启用状态;ENABLED
            */
        private String enabled;
            /**
            *  删除状态;DELETED
            */
        private String deleted;
            /**
            *  乐观锁
            */
        private BigDecimal version;
            /**
            *  备注
            */
        private String remarks;

/**
* ------------------流程定义KEY--------------------
*/
    String procDefKey;
}