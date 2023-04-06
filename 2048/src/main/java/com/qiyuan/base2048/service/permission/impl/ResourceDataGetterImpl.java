package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mapstruct.TbResourceStructMapper;
import com.qiyuan.base2048.mapper.mybatis.TbResourceMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.pojo.vo.eltable.MenuVO;
import com.qiyuan.base2048.service.permission.ResourceDataGetter;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.ResourceTypeEnum;
import com.qiyuan.bautil.util.StringTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-01 22:54
 **/
@Service
public class ResourceDataGetterImpl implements ResourceDataGetter {
    @Resource
    private TbResourceMapper tbResourceMapper;

    @Override
    public MenuDTO getMenuDtoByUri(String uri) throws Exception {
        QueryWrapper<TbResource> queryWrapper = new QueryWrapper();
        queryWrapper.eq("uri",uri)
                .eq("deleted", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("enabled", IsEnabledEnum.ENABLED.getStringValue());
        TbResource tbResource = tbResourceMapper.selectOne(queryWrapper);
        MenuDTO menuDTO = TbResourceStructMapper.INSTANCE.map2MenuDTO(tbResource);
        return menuDTO;
    }

    @Override
    public List<MenuDTO> getMyselfMenuList(BaseUser baseUser) throws Exception {
        List<String> roles = baseUser.getUserRoles();
        String roleString = "'"+ StringTool.join("','",roles)+"'";
        QueryWrapper<TbResource> queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("enabled", IsEnabledEnum.ENABLED.getStringValue())
                .and(wrapper -> wrapper.inSql("guid","select resource_guid from tr_role_resource where deleted="+IsDeletedEnum.NOTDELETED.getValue()+" and enabled="+IsEnabledEnum.ENABLED.getValue()+" and ROLE_GUID in ("+roleString+")")
                        .or().eq("access_control_style","02"));
        List<TbResource> tbResourceList = tbResourceMapper.selectList(queryWrapper);
        List<MenuDTO> menuDTOList = TbResourceStructMapper.INSTANCE.map2MenuDtoList(tbResourceList);
        return menuDTOList;
    }

    @Override
    public IPage<TbResource> get(String roleGuid, String resourceName, String uri, String resourceType, String enabled, Integer currentPage, Integer pageSize) throws Exception {
        QueryWrapper<TbResource> wrapper = new QueryWrapper();
        if(!StringUtils.isBlank(resourceName)){
            wrapper.like("name",resourceName);
        }
        if(!StringUtils.isBlank(uri)){
            wrapper.likeLeft("uri",uri);
        }
        if(!StringUtils.isBlank(resourceType)){
            wrapper.eq("RESOURCE_TYPE",resourceType);
        }
        if(!StringUtils.isBlank(enabled)){
            wrapper.eq("ENABLED",enabled);
        }
        if(!StringUtils.isBlank(roleGuid)) {
            wrapper.inSql("guid", "select resource_guid from tr_role_resource where role_guid='" + roleGuid + "' and enabled="+IsEnabledEnum.ENABLED.getValue()+" and deleted="+IsDeletedEnum.NOTDELETED.getValue());
        }

        IPage<TbResource> tbResourceIPage = new Page<>(currentPage,pageSize);
        tbResourceIPage = tbResourceMapper.selectPage(tbResourceIPage,wrapper);

        return tbResourceIPage;
    }
    @Override
    public IPage<TbResource> getByRoleId(String roleGuid, Integer currentPage, Integer pageSize) throws Exception {
        if(StringUtils.isBlank(roleGuid)){
            return null;
        }
        QueryWrapper<TbResource> wrapper = new QueryWrapper();
        wrapper.inSql("guid","select resource_guid from tr_role_resource where roleid='"+roleGuid+"' and enabled="+IsEnabledEnum.ENABLED.getValue()+" and deleted="+IsDeletedEnum.NOTDELETED.getValue());

        IPage<TbResource> tbResourceIPage = new Page<>(currentPage,pageSize);
        tbResourceIPage = tbResourceMapper.selectPage(tbResourceIPage,wrapper);

        return tbResourceIPage;
    }

    @Override
    public IPage<MenuVO> getTopMenu(String roleGuid, String resourceName, String uri, String enabled, Integer currentPage, Integer pageSize) throws Exception {
        QueryWrapper<TbResource> wrapper = new QueryWrapper();
        wrapper.eq("RESOURCE_TYPE", ResourceTypeEnum.MENU.getValue());
        wrapper.eq("PARENT_GUID", "top");
        /** 咱只查未删除的 **/
        wrapper.eq("DELETED",IsDeletedEnum.NOTDELETED.getValue());
        if(!StringUtils.isBlank(resourceName)){
            wrapper.like("name",resourceName);
        }
        if(!StringUtils.isBlank(uri)){
            wrapper.likeLeft("uri",uri);
        }
        if(!StringUtils.isBlank(enabled)){
            wrapper.eq("ENABLED",IsEnabledEnum.ENABLED.getStringValue());
        }
        if(!StringUtils.isBlank(roleGuid)) {
            wrapper.inSql("guid", "select resource_guid from tr_role_resource where role_guid='" + roleGuid + "' and enabled="+IsEnabledEnum.ENABLED.getValue()+" and deleted="+IsDeletedEnum.NOTDELETED.getValue());
        }
        wrapper.orderByAsc("SORT");
        IPage tbResourceIPage = new Page<>(currentPage,pageSize);
        tbResourceIPage = tbResourceMapper.selectPage(tbResourceIPage,wrapper);
        tbResourceIPage.setRecords(TbResourceStructMapper.INSTANCE.map2MenuVoList(tbResourceIPage.getRecords()));
        for(Object menuVO:tbResourceIPage.getRecords()){
            ((MenuVO)menuVO).setHasChildren(true);
        }

        return tbResourceIPage;
    }

    @Override
    public List<MenuVO> getAllChilden(String guid) throws Exception {
        QueryWrapper<TbResource> wrapper = new QueryWrapper();
        wrapper.eq("PARENT_GUID", guid);
        wrapper.eq("DELETED",IsDeletedEnum.NOTDELETED.getStringValue());
        wrapper.orderByAsc("SORT");

        List<TbResource> tbResourceList = tbResourceMapper.selectList(wrapper);

        List<MenuVO> menuVOList = TbResourceStructMapper.INSTANCE.map2MenuVoList(tbResourceList);
        for(MenuVO menuVO:menuVOList){
            menuVO.setHasChildren(true);
        }

        return menuVOList;
    }
}
