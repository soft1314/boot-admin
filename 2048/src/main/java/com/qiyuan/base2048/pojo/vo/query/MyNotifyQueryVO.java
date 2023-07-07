package com.qiyuan.base2048.pojo.vo.query;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import lombok.Data;

import java.util.Date;

@Data
public class MyNotifyQueryVO extends BasePageQueryVO {
    /**
     * 用户GUID
     */
    private String userGuid;
    /**
     * 是否已阅
     */
    private String haveRead;
    /**
     *  类别;NOTIFY_TYPE
     */
    private String notifyType;
    /**
     *  标题
     */
    private String title;
    /**
     *  关键词
     */
    private String keyword;
    /**
     *  发布时间
     */
    private Date releaseTime;
    /**
     *  发布人
     */
    private String releaseBy;
}
