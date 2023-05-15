package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mybatis.ToNotifyRecordMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotifyRecord;
import com.qiyuan.base2048.service.mybatis.IToNotifyRecordService;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告用户接收阅读表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-9 14:24:58
*/
@Service
public class ToNotifyRecordServiceImpl extends ServiceImpl<ToNotifyRecordMapper, ToNotifyRecord> implements IToNotifyRecordService {
    /**
     * 获取已发送LIST
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    @Override
    public List<ToNotifyRecord> selectByNotifyGuid(String notifyGuid) throws Exception {
        QueryWrapper<ToNotifyRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NOTIFY_GUID", notifyGuid)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        List<ToNotifyRecord> toNotifyRecordList = this.list(queryWrapper);
        return toNotifyRecordList;
    }
}