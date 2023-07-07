package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.TbOrganizationMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbOrganization;
import com.qiyuan.base2048.pojo.vo.query.NotifyOrgQueryVO;
import com.qiyuan.base2048.service.oa.NotifyOrgRelationGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyOrgRelationGetterImpl implements NotifyOrgRelationGetter {
    @Resource
    private TbOrganizationMapper tbOrganizationMapper;

    @Override
    public ResultDTO fetchGrantedPage(NotifyOrgQueryVO notifyOrgQueryVO) throws Exception {
        QueryWrapper<TbOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .inSql("GUID", "select ORG_GUID from TR_NOTIFY_ORG where NOTIFY_GUID='" + notifyOrgQueryVO.getNotifyGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(notifyOrgQueryVO.getOrgCode())) {
            queryWrapper.like("CODE", notifyOrgQueryVO.getOrgCode());
        }
        if (StringUtils.isNotBlank(notifyOrgQueryVO.getOrgName())) {
            queryWrapper.like("NAME", notifyOrgQueryVO.getOrgName());
        }
        if(!notifyOrgQueryVO.getIgnoreAdmDiv()) {
            if (StringUtils.isNotBlank(notifyOrgQueryVO.getAdmDivCode())) {
                queryWrapper.eq("DIV_CODE", notifyOrgQueryVO.getAdmDivCode());
            }
        }
        queryWrapper.orderByAsc("DIV_CODE").orderByAsc("CODE");

        IPage<TbOrganization> tbOrgPage = new Page<>(notifyOrgQueryVO.getCurrentPage(), notifyOrgQueryVO.getPageSize());
        tbOrgPage = tbOrganizationMapper.selectPage(tbOrgPage, queryWrapper);
        return ResultDTO.success(tbOrgPage);
    }

    @Override
    public ResultDTO fetchGrantablePage(NotifyOrgQueryVO notifyOrgQueryVO) throws Exception {
        QueryWrapper<TbOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("LEAF", YesNoEnum.YES.getValue())
                .notInSql("GUID", "select ORG_GUID from TR_NOTIFY_ORG where NOTIFY_GUID='" + notifyOrgQueryVO.getNotifyGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(notifyOrgQueryVO.getOrgCode())) {
            queryWrapper.like("CODE", notifyOrgQueryVO.getOrgCode());
        }
        if (StringUtils.isNotBlank(notifyOrgQueryVO.getOrgName())) {
            queryWrapper.like("NAME", notifyOrgQueryVO.getOrgName());
        }
        if(!notifyOrgQueryVO.getIgnoreAdmDiv()) {
            if (StringUtils.isNotBlank(notifyOrgQueryVO.getAdmDivCode())) {
                queryWrapper.eq("DIV_CODE", notifyOrgQueryVO.getAdmDivCode());
            }
        }
        queryWrapper.orderByAsc("CODE");

        IPage<TbOrganization> tbOrgPage = new Page<>(notifyOrgQueryVO.getCurrentPage(), notifyOrgQueryVO.getPageSize());
        tbOrgPage = tbOrganizationMapper.selectPage(tbOrgPage, queryWrapper);
        return ResultDTO.success(tbOrgPage);
    }
}
