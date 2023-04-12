package com.qiyuan.base2048.pojo.vo.generated;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（业务工作流）报销实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:39:11
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "示例表（业务工作流）报销实体类")
public class DemoExpenseScene4QueryVO extends BasePageQueryVO {

        /**
        * 主键
        */
    private String guid;
            /**
            *  标题
            */
        private String title;
            /**
            *  报销金额
            */
        private String money;
            /**
            *  情况说明
            */
        private String note;
            /**
            *  业务日期
            */
        private Date bizDate;
            /**
            *  报销人
            */
        private String empName;
            /**
            *  职称;TECH_TITLE;LAZY
            */
        private String techTitle;
            /**
            *  员工级别;EMP_LEVEL
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
            *  流程定义ID
            */
        private String processDefinitionId;
            /**
            *  流程实例ID
            */
        private String processInstanceId;
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
        private String version;
            /**
            *  备注
            */
        private String remarks;
}