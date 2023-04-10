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
    * 示例表（层次CRUD）单位表实体类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:11:10
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "示例表（层次CRUD）单位表实体类")
public class DemoOrgScene2QueryVO extends BasePageQueryVO {

        /**
        * 主键
        */
    private String guid;
            /**
            *  单位名称
            */
        private String name;
            /**
            *  社会信用代码
            */
        private String code;
            /**
            *  组织类别;ORGTYPE
            */
        private String orgtype;
            /**
            *  行业类别;INDUSTRY_CATEGORY;LAZY
            */
        private String industryCategory;
            /**
            *  成立时间
            */
        private Date foundTime;
            /**
            *  单位级别;UNITLEVEL
            */
        private String rankCode;
            /**
            *  编制人数
            */
        private BigDecimal num;
            /**
            *  办公地址
            */
        private String addr;
            /**
            *  负责人
            */
        private String leader;
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
            *  是否末级;YESNO
            */
        private String leaf;
            /**
            *  父级GUID
            */
        private String parentGuid;
            /**
            *  顺序号
            */
        private BigDecimal sort;
}