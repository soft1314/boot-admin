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
    * 用户认证日志表实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "用户认证日志表实体类")
public class TbLogAuthScene1QueryVO extends BasePageQueryVO {

        /**
        * 
        */
    private String guid;
            /**
            *  登录账号
            */
        private String userName;
            /**
            *  结果类型;RESULT_TYPE
            */
        private String resultType;
            /**
            *  IP地址
            */
        private String operIp;
            /**
            *  消息内容
            */
        private String msg;
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