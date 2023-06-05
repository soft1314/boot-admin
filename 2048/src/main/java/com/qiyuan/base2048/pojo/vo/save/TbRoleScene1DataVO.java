package com.qiyuan.base2048.pojo.vo.save;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 系统角色基础表实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "系统角色基础表实体类")
public class TbRoleScene1DataVO implements Serializable {

private static final long serialVersionUID = 1L;
        /**
        * 主键
        */
    private String guid;
            /**
            *  角色编码
            */
        private String code;
            /**
            *  角色名称
            */
        private String name;
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