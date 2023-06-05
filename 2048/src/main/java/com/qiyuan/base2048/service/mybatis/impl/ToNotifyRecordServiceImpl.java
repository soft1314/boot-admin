package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mybatis.ToNotifyRecordMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotifyRecord;
import com.qiyuan.base2048.service.mybatis.IToNotifyRecordService;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.UserTool;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告用户接收阅读表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
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

    @Override
    public ResultDTO setReaded(String notifyGuid) throws Exception {
        BaseUser baseUser = UserTool.getBaseUser();
        UpdateWrapper<ToNotifyRecord> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("NOTIFY_GUID",notifyGuid)
                .eq("USER_GUID",baseUser.getUserGuid())
                .eq("deleted", IsDeletedEnum.NOTDELETED.getValue())
                .eq("enabled",IsEnabledEnum.ENABLED.getStringValue())
                .eq("HAVE_READ",YesNoEnum.NO.getValue())
                .set("HAVE_READ", YesNoEnum.YES.getValue())
                .set("READ_TIME",new Date());
        if(this.update(updateWrapper)){
            return ResultDTO.successCustom("已设置为已读状态。");
        }
        return ResultDTO.success();
    }
}