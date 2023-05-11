package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.TbEmpMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbEmp;
import com.qiyuan.base2048.pojo.vo.query.NotifyEmpQueryVO;
import com.qiyuan.base2048.service.oa.NotifyEmpRelationGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyEmpRelationGetterImpl implements NotifyEmpRelationGetter {
    @Resource
    private TbEmpMapper tbEmpMapper;

    @Override
    public ResultDTO fetchGrantedPage(NotifyEmpQueryVO notifyEmpQueryVO) throws Exception {
        QueryWrapper<TbEmp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .inSql("GUID", "select EMP_GUID from TR_NOTIFY_EMP where NOTIFY_GUID='" + notifyEmpQueryVO.getNotifyGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(notifyEmpQueryVO.getLogonName())) {
            queryWrapper.inSql("GUID","select EMP_GUID from TB_USER where LOGON_NAME='"+notifyEmpQueryVO.getLogonName()+"' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        }
        if (StringUtils.isNotBlank(notifyEmpQueryVO.getEmpName())) {
            queryWrapper.like("NAME", notifyEmpQueryVO.getEmpName());
        }
        if(!notifyEmpQueryVO.getIgnoreAdmDiv()) {
            if (StringUtils.isNotBlank(notifyEmpQueryVO.getAdmDivCode())) {
                queryWrapper.eq("ADM_DIV_CODE", notifyEmpQueryVO.getAdmDivCode());
            }
        }
        queryWrapper.orderByAsc("ADM_DIV_CODE").orderByAsc("ORG_CODE").orderByAsc("NAME");

        IPage<TbEmp> tbEmpPage = new Page<>(notifyEmpQueryVO.getCurrentPage(), notifyEmpQueryVO.getPageSize());
        tbEmpPage = tbEmpMapper.selectPage(tbEmpPage, queryWrapper);
        return ResultDTO.success(tbEmpPage);
    }

    @Override
    public ResultDTO fetchGrantablePage(NotifyEmpQueryVO notifyEmpQueryVO) throws Exception {
        QueryWrapper<TbEmp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .notInSql("GUID", "select EMP_GUID from TR_NOTIFY_EMP where NOTIFY_GUID='" + notifyEmpQueryVO.getNotifyGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'")
                .inSql("GUID","select EMP_GUID from TB_USER where ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(notifyEmpQueryVO.getLogonName())) {
            queryWrapper.inSql("GUID","select EMP_GUID from TB_USER where LOGON_NAME='"+notifyEmpQueryVO.getLogonName()+"' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        }
        if (StringUtils.isNotBlank(notifyEmpQueryVO.getEmpName())) {
            queryWrapper.like("NAME", notifyEmpQueryVO.getEmpName());
        }
        if(!notifyEmpQueryVO.getIgnoreAdmDiv()) {
            if (StringUtils.isNotBlank(notifyEmpQueryVO.getAdmDivCode())) {
                queryWrapper.eq("ADM_DIV_CODE", notifyEmpQueryVO.getAdmDivCode());
            }
        }
        queryWrapper.orderByAsc("ADM_DIV_CODE").orderByAsc("ORG_CODE").orderByAsc("NAME");

        IPage<TbEmp> tbEmpPage = new Page<>(notifyEmpQueryVO.getCurrentPage(), notifyEmpQueryVO.getPageSize());
        tbEmpPage = tbEmpMapper.selectPage(tbEmpPage, queryWrapper);
        return ResultDTO.success(tbEmpPage);
    }
}
