package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.TbOrganizationMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbOrganization;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.query.DataPermissionQueryVO;
import com.qiyuan.base2048.service.permission.UserOrgRelationGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.StringTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserOrgRelationGetterImpl implements UserOrgRelationGetter {
    @Resource
    private TbOrganizationMapper tbOrganizationMapper;
    @Value("${base2048.default.admin-div.value}")
    private List<String> defaultAdminDivValue;

    @Override
    public ResultDTO fetchGrantedPage(DataPermissionQueryVO queryByGuidDTO) throws Exception {
        QueryWrapper<TbOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .inSql("CODE", "select ORG_CODE from TR_USER_ORG where USER_GUID='" + queryByGuidDTO.getGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(queryByGuidDTO.getCode())) {
            queryWrapper.like("CODE", queryByGuidDTO.getCode());
        }
        if (StringUtils.isNotBlank(queryByGuidDTO.getName())) {
            queryWrapper.like("NAME", queryByGuidDTO.getName());
        }
        queryWrapper.orderByAsc("CODE");

        IPage<TbOrganization> tbOrgPage = new Page<>(queryByGuidDTO.getCurrentPage(), queryByGuidDTO.getPageSize());
        tbOrgPage = tbOrganizationMapper.selectPage(tbOrgPage, queryWrapper);
        return ResultDTO.success(tbOrgPage);
    }

    @Override
    public ResultDTO fetchGrantablePage(DataPermissionQueryVO queryByGuidDTO) throws Exception {
        QueryWrapper<TbOrganization> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("LEAF", YesNoEnum.YES.getValue())
                .likeRight("DIV_CODE", this.getDefaultAdmDivCode())
                .notInSql("CODE", "select ORG_CODE from TR_USER_ORG where USER_GUID='" + queryByGuidDTO.getGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(queryByGuidDTO.getCode())) {
            queryWrapper.like("CODE", queryByGuidDTO.getCode());
        }
        if (StringUtils.isNotBlank(queryByGuidDTO.getName())) {
            queryWrapper.like("NAME", queryByGuidDTO.getName());
        }
        queryWrapper.orderByAsc("CODE");

        IPage<TbOrganization> tbOrgPage = new Page<>(queryByGuidDTO.getCurrentPage(), queryByGuidDTO.getPageSize());
        tbOrgPage = tbOrganizationMapper.selectPage(tbOrgPage, queryWrapper);
        return ResultDTO.success(tbOrgPage);
    }

    private String getDefaultAdmDivCode() throws Exception {
        if (defaultAdminDivValue == null) {
            return "37";
        }
        if (defaultAdminDivValue.size() == 0) {
            return "37";
        }
        String divCode = defaultAdminDivValue.get(defaultAdminDivValue.size() - 1);
        return StringTool.trimEndLoop(divCode, "00");
    }
}
