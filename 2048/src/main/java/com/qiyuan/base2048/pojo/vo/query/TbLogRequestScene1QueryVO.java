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
    * 访问日志表实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "访问日志表实体类")
public class TbLogRequestScene1QueryVO extends BasePageQueryVO {

        /**
        * 
        */
    private String guid;
            /**
            *  IP地址
            */
        private String operIp;
            /**
            *  访问点
            */
        private String uri;
            /**
            *  异常消息
            */
        private String errorMsg;
            /**
            *  访问方法
            */
        private String method;
            /**
            *  请求方式
            */
        private String requestMethod;
            /**
            *  消耗时间
            */
        private String costTime;
            /**
            *  业务类型;BUSINESS_TYPE
            */
        private String businessType;
            /**
            *  操作模块
            */
        private String businessModule;
            /**
            *  操作人员类别;OPERATOR_TYPE
            */
        private String operatorType;
            /**
            *  请求参数
            */
        private String operParam;
            /**
            *  响应结果
            */
        private String jsonResult;
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