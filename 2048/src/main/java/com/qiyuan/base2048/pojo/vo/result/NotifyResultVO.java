package com.qiyuan.base2048.pojo.vo.result;

import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import lombok.Data;

import java.util.Date;

@Data
public class NotifyResultVO extends ToNotify {
    /**
     * 是否已读
     */
    private String haveRead;
    /**
     * 阅读时间
     */
    private Date readTime;
}
