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
    * 行政区划表实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-24 8:56:52
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "行政区划表实体类")
public class TbAdmDivScene2DataVO implements Serializable {

private static final long serialVersionUID = 1L;
        /**
        * 主键
        */
    private String guid;
            /**
            *  行政区划代码
            */
        private String admDivCode;
            /**
            *  行政区划名称
            */
        private String admDivName;
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
            *  父级GUID
            */
        private String parentGuid;
            /**
            *  是否末级;YESNO
            */
        private String leaf;
            /**
            *  顺序号
            */
        private BigDecimal sort;
    /** 是否末级 **/
    private boolean hasChildren;
}