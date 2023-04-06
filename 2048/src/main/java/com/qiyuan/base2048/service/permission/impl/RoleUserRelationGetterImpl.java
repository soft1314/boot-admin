package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.TbUserMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.query.RolePermissionQueryVO;
import com.qiyuan.base2048.service.permission.RoleUserRelationGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleUserRelationGetterImpl implements RoleUserRelationGetter {
    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public ResultDTO fetchGrantedPage(RolePermissionQueryVO rolePermissionQueryVO) throws Exception {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .inSql("GUID", "select USER_GUID from TR_ROLE_USER where ROLE_GUID='" + rolePermissionQueryVO.getRoleGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getCode())) {
            queryWrapper.eq("LOGON_NAME", rolePermissionQueryVO.getCode());
        }
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getName())) {
            queryWrapper.like("FIRST_NAME", rolePermissionQueryVO.getName());
        }
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getLastName())) {
            queryWrapper.like("LAST_NAME", rolePermissionQueryVO.getLastName());
        }
        queryWrapper.orderByAsc("FIRST_NAME");

        IPage<TbUser> tbUserIPage = new Page<>(rolePermissionQueryVO.getCurrentPage(), rolePermissionQueryVO.getPageSize());
        tbUserIPage = tbUserMapper.selectPage(tbUserIPage, queryWrapper);
        return ResultDTO.success(tbUserIPage);
    }

    @Override
    public ResultDTO fetchGrantablePage(RolePermissionQueryVO rolePermissionQueryVO) throws Exception {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .notInSql("GUID", "select USER_GUID from TR_ROLE_USER where ROLE_GUID='" + rolePermissionQueryVO.getRoleGuid() + "' and ENABLED='" + IsEnabledEnum.ENABLED.getStringValue() + "' and DELETED='" + IsDeletedEnum.NOTDELETED.getStringValue() + "'");
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getCode())) {
            queryWrapper.eq("LOGON_NAME", rolePermissionQueryVO.getCode());
        }
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getName())) {
            queryWrapper.like("FIRST_NAME", rolePermissionQueryVO.getName());
        }
        if (StringUtils.isNotBlank(rolePermissionQueryVO.getLastName())) {
            queryWrapper.like("LAST_NAME", rolePermissionQueryVO.getLastName());
        }
        queryWrapper.orderByAsc("FIRST_NAME");

        IPage<TbUser> tbUserIPage = new Page<>(rolePermissionQueryVO.getCurrentPage(), rolePermissionQueryVO.getPageSize());
        tbUserIPage = tbUserMapper.selectPage(tbUserIPage, queryWrapper);
        return ResultDTO.success(tbUserIPage);
    }
}
