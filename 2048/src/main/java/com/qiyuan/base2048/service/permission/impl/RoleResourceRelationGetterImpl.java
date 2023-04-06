package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.TbResourceMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbOrganization;
import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.pojo.vo.query.ResourcePageQueryParamVO;
import com.qiyuan.base2048.pojo.vo.query.RolePermissionQueryVO;
import com.qiyuan.base2048.service.permission.RoleResourceRelationGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleResourceRelationGetterImpl implements RoleResourceRelationGetter {
    @Resource
    private TbResourceMapper tbResourceMapper;

    @Override
    public ResultDTO fetchGrantedPage(RolePermissionQueryVO rolePermissionQueryVO) throws Exception {
        QueryWrapper<TbResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .inSql("GUID", "select RESOURCE_GUID from TR_ROLE_RESOURCE where ROLE_GUID='" + rolePermissionQueryVO.getRoleGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getCode())) {
            queryWrapper.eq("RESOURCE_TYPE", rolePermissionQueryVO.getCode());
        }
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getName())) {
            queryWrapper.like("NAME", rolePermissionQueryVO.getName());
        }
        if(StringUtils.isNotBlank(rolePermissionQueryVO.getLastName())){
            queryWrapper.eq("URI",rolePermissionQueryVO.getLastName());
        }
        queryWrapper.orderByAsc("URI");

        IPage<TbResource> tbResourceIPage = new Page<>(rolePermissionQueryVO.getCurrentPage(), rolePermissionQueryVO.getPageSize());
        tbResourceIPage = tbResourceMapper.selectPage(tbResourceIPage, queryWrapper);
        return ResultDTO.success(tbResourceIPage);
    }

    @Override
    public ResultDTO fetchGrantablePage(RolePermissionQueryVO rolePermissionQueryVO) throws Exception {
        QueryWrapper<TbResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .notInSql("GUID", "select RESOURCE_GUID from TR_ROLE_RESOURCE where ROLE_GUID='" + rolePermissionQueryVO.getRoleGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getCode())) {
            queryWrapper.eq("RESOURCE_TYPE", rolePermissionQueryVO.getCode());
        }
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getName())) {
            queryWrapper.like("NAME", rolePermissionQueryVO.getName());
        }
        if(StringUtils.isNotBlank(rolePermissionQueryVO.getLastName())){
            queryWrapper.eq("URI",rolePermissionQueryVO.getLastName());
        }
        queryWrapper.orderByAsc("URI");

        IPage<TbResource> tbResourceIPage = new Page<>(rolePermissionQueryVO.getCurrentPage(), rolePermissionQueryVO.getPageSize());
        tbResourceIPage = tbResourceMapper.selectPage(tbResourceIPage, queryWrapper);
        return ResultDTO.success(tbResourceIPage);
    }
}
