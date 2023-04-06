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
    * 示例表（基本CRUD）人员表实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:09:52
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "示例表（基本CRUD）人员表实体类")
public class DemoEmpScene1QueryVO extends BasePageQueryVO {

        /**
        * 主键
        */
    private String guid;
            /**
            *  姓名
            */
        private String name;
            /**
            *  性别;SEX
            */
        private String sex;
            /**
            *  出生年月
            */
        private Date birthday;
            /**
            *  职称;TECH_TITLE;LAZY
            */
        private String techTitle;
            /**
            *  薪水
            */
        private BigDecimal salary;
            /**
            *  情况说明
            */
        private String note;
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
}