package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyOrg;
import com.qiyuan.base2048.mapper.mybatis.TrNotifyOrgMapper;
import com.qiyuan.base2048.service.mybatis.ITrNotifyOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 按单位通知关联表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class TrNotifyOrgServiceImpl extends ServiceImpl<TrNotifyOrgMapper, TrNotifyOrg> implements ITrNotifyOrgService {
    /**
     * 获取需要发送的机构LIST
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    @Override
    public List<TrNotifyOrg> getTrNotifyOrgList(String notifyGuid) throws Exception {
        QueryWrapper<TrNotifyOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NOTIFY_GUID", notifyGuid)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        List<TrNotifyOrg> trNotifyOrgList = this.list(queryWrapper);
        return trNotifyOrgList;
    }
}