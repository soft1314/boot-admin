package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.mybatis.ITbResourceService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.service.permission.ResourceDataSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.ResourceTypeEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-01 22:57
 **/
@Service
public class ResourceDataSaverImpl implements ResourceDataSaver {
    @Resource
    private ITbResourceService iTbResourceService;

    @Override
    public boolean updateResource(TbResource tbResource) throws Exception {
        return iTbResourceService.updateById(tbResource);
    }

    @Override
    public boolean insertResource(TbResource tbResource) throws Exception {
        if(ResourceTypeEnum.FUNCTION.getValue().equals(tbResource.getResourceType())){
            if(tbResource.getSort() == null){
                tbResource.setSort(BigDecimal.ZERO);
            }
            if(StringUtils.isBlank(tbResource.getParentGuid())){
                tbResource.setParentGuid("function");
            }
            if(StringUtils.isBlank(tbResource.getLeaf())){
                tbResource.setLeaf(YesNoEnum.YES.getValue());
            }
        }
        return iTbResourceService.save(tbResource);
    }

    @Override
    public boolean insertOrUpdateResource(TbResource tbResource) throws Exception {
        if(StringUtils.isBlank(tbResource.getGuid())){
            if(StringUtils.isBlank(tbResource.getLeaf())){
                tbResource.setLeaf(YesNoEnum.YES.getValue());
            }
            return iTbResourceService.save(tbResource);
        }else{
            return iTbResourceService.updateById(tbResource);
        }
    }

    /**
     * 对于功能点，直接逻辑删除
     * 对于菜单，先判断是否存在未删除的子菜单，如果存在，不允许删除
     * @param guid
     * @return
     * @throws Exception
     */
    @Override
    public ResultDTO deleteByGuid(String guid) throws Exception {
        TbResource tbResource = iTbResourceService.getById(guid);
        if(ResourceTypeEnum.MENU.getValue().equals(tbResource.getResourceType())){
            QueryWrapper<TbResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PARENT_GUID",guid).eq("DELETED",IsDeletedEnum.NOTDELETED.getValue());
            if(iTbResourceService.count(queryWrapper) > 0){
                return ResultDTO.failureCustom(MessageUtils.get("resource.del.error.exists.submenu"));
            }
        }
        UpdateWrapper<TbResource> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
        if(iTbResourceService.update(updateWrapper)){
            return ResultDTO.successCustom(MessageUtils.get("resource.del.sucess"),null);
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("resource.del.error"));
        }
    }
}
