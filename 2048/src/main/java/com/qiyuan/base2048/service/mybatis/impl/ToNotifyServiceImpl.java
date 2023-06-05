package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import com.qiyuan.base2048.mapper.mybatis.ToNotifyMapper;
import com.qiyuan.base2048.service.mybatis.IToNotifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.UserTool;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告主表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class ToNotifyServiceImpl extends ServiceImpl<ToNotifyMapper, ToNotify> implements IToNotifyService {
    @Override
    public void changeToPublished(String notifyGuid) throws Exception {
        String operator = UserTool.getOperator();
        UpdateWrapper<ToNotify> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("HAVE_RELEASE", YesNoEnum.YES.getValue())
                .set("RELEASE_TIME",new Date())
                .set("RELEASE_BY",operator)
                .eq("GUID",notifyGuid);
        this.update(updateWrapper);
    }
}