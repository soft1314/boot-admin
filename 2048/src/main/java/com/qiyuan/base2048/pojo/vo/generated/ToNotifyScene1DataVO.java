package com.qiyuan.base2048.pojo.vo.generated;

import com.qiyuan.bautil.annotate.valid.EnumValid;
import com.qiyuan.bautil.enums.NotifyTypeEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 通知通告主表实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-5-8 10:38:33
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "通知通告主表实体类")
public class ToNotifyScene1DataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String guid;
    /**
     * 类别;NOTIFY_TYPE
     */
    @EnumValid(target = NotifyTypeEnum.class, message = "")
    private String notifyType;
    /**
     * 标题
     */
    @NotBlank(message = "标题是必填项。")
    private String title;
    /**
     * 内容概要
     */
    @NotBlank(message = "概要是必填项。")
    private String summary;
    /**
     * 关键词
     */
    @NotBlank(message = "关键词是必填项。")
    private String keyword;
    /**
     * 是否已发布;YESNO
     */
    private String haveRelease;
    /**
     * 发布时间
     */
    private Date releaseTime;
    /**
     * 发布人
     */
    private String releaseBy;
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
    private String version;
    /**
     * 备注
     */
    private String remarks;
}