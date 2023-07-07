package com.qiyuan.base2048.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.pojo.vo.eltable.MenuVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.MenuDTO;

import java.util.List;

public interface ResourceDataGetter {
    List<MenuDTO> getMyselfMenuList(BaseUser baseUser) throws Exception;
    MenuDTO getMenuDtoByUri(String uri) throws Exception;
    /**
     * 根据条件查询系统功能和菜单数据
     * @param uri
     * @param resourceType
     * @param enabled
     * @param currentPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    IPage<TbResource> get(String roleId, String funName, String uri, String resourceType, String enabled, Integer currentPage, Integer pageSize) throws Exception;
    IPage<TbResource> getByRoleId(String roleGuid,Integer currentPage, Integer pageSize) throws Exception;
    IPage<MenuVO> getTopMenu(String roleId, String funName, String uri, String enabled, Integer currentPage, Integer pageSize) throws Exception;
    List<MenuVO> getAllChilden(String guid) throws Exception;

}
