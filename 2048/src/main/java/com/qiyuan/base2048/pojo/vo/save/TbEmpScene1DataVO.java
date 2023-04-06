package com.qiyuan.base2048.pojo.vo.save;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 人员表实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2022-12-5 16:40:14
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "人员表实体类")
public class TbEmpScene1DataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String guid;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别;SEX
     */
    private String sex;
    /**
     * 入职时间
     */
    private Date entryTime;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 职务;TEST;LAZY
     */
    private String job;
    /**
     * 行政级
     */
    private BigDecimal adminLevel;
    /**
     * 技术级
     */
    private BigDecimal techLevel;
    /**
     * 职称;TECH_TITLE;LAZY
     */
    private String techTitle;
    /**
     * 办公电话
     */
    private String telephone;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 地址
     */
    private String address;
    /**
     * 启用状态;ENABLED
     */
    private String enabled;
    /**
     * 删除状态;DELETED
     */
    private String deleted;
    /**
     * 乐观锁
     */
    private BigDecimal version;
    /**
     * 备注
     */
    private String remarks;

    private String admDivCode;
    private String admDivName;
    private String orgCode;
    private String orgName;
}