package com.qiyuan.base2048.pojo.vo.query;


import com.qiyuan.bautil.dto.BasePageQueryVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

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
public class TbRoleScene1QueryVO extends BasePageQueryVO {

        /**
        * 主键
        */
    private String guid;
            /**
            *  记录创建者
            */
        private String createBy;
            /**
            *  角色编码
            */
        private String code;
            /**
            *  角色名称
            */
        private String name;
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