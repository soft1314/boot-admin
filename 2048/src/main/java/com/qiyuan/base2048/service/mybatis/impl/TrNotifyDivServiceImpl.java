package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mybatis.TrNotifyDivMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyDiv;
import com.qiyuan.base2048.service.mybatis.ITrNotifyDivService;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 按区划通知关联表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class TrNotifyDivServiceImpl extends ServiceImpl<TrNotifyDivMapper, TrNotifyDiv> implements ITrNotifyDivService {
    @Override
    public List<TrNotifyDiv> selectByNotifyGuid(String notifyGuid) throws Exception {
        QueryWrapper<TrNotifyDiv> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NOTIFY_GUID",notifyGuid)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .orderByAsc("DIV_CODE");
        List<TrNotifyDiv> trNotifyDivs = this.list(queryWrapper);
        return trNotifyDivs;
    }
}