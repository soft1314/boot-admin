package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.query.UserPageQueryVO;
import com.qiyuan.base2048.service.mybatis.ITbUserService;
import com.qiyuan.base2048.service.permission.UserDataGetter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-09 21:55
 **/
@Service
public class UserDataGetterImpl implements UserDataGetter {
    @Resource
    private ITbUserService tbUserService;

    @Override
    public IPage<TbUser> getPage(UserPageQueryVO userPageQueryVO) throws Exception {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(userPageQueryVO.getLogonName())){
            queryWrapper.like("LOGON_NAME",userPageQueryVO.getLogonName());
        }
        if(StringUtils.isNotBlank(userPageQueryVO.getFirstName())){
            queryWrapper.like("FIRST_NAME",userPageQueryVO.getFirstName());
        }
        if(StringUtils.isNotBlank(userPageQueryVO.getLastName())){
            queryWrapper.like("LAST_NAME",userPageQueryVO.getLastName());
        }
        if(StringUtils.isNotBlank(userPageQueryVO.getOrgType())){
            queryWrapper.eq("ORG_TYPE",userPageQueryVO.getOrgType());
        }
        if(userPageQueryVO.getEnabled() != null){
            queryWrapper.eq("ENABLED",userPageQueryVO.getEnabled());
        }
        if(userPageQueryVO.getDeleted() != null){
            queryWrapper.eq("DELETED",userPageQueryVO.getDeleted());
        }
        if(StringUtils.isNotBlank(userPageQueryVO.getRoleId())) {
            queryWrapper.inSql("GUID", "select USER_GUID from TR_ROLE_USER where ROLE_GUID='" + userPageQueryVO.getRoleId() + "'");
        }

        IPage<TbUser> tbUserPage = new Page<>(userPageQueryVO.getCurrentPage(),userPageQueryVO.getPageSize());
        tbUserPage = tbUserService.page(tbUserPage,queryWrapper);

        return tbUserPage;
    }
}
