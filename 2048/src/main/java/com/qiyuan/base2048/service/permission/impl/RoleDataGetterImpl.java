package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.TbRoleMapper;
import com.qiyuan.base2048.mapper.mybatis.TrRoleResourceMapper;
import com.qiyuan.base2048.mapper.mybatis.TrRoleUserMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleUser;
import com.qiyuan.base2048.service.permission.RoleDataGetter;
import com.qiyuan.bautil.dto.TransferDataDTO;
import com.qiyuan.bautil.dto.TransferItem;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.util.ReflectionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-07 22:32
 **/
@Service
@Slf4j
public class RoleDataGetterImpl implements RoleDataGetter {
    @Resource
    private TbRoleMapper tbRoleMapper;
    @Resource
    private TrRoleResourceMapper trRoleResourceMapper;
    @Resource
    private TrRoleUserMapper trRoleUserMapper;


    @Override
    public TransferDataDTO getResourceRoleTransferData(String resourceGuid) throws Exception {
        List<TransferItem> allPowerItem = tbRoleMapper.getAllEnabledRols(IsEnabledEnum.ENABLED.getValue(), IsDeletedEnum.NOTDELETED.getValue());
        List<String> hasPower = trRoleResourceMapper.selectResourceRoleListByResourceGuid(resourceGuid,IsEnabledEnum.ENABLED.getStringValue(), IsDeletedEnum.NOTDELETED.getStringValue());
        TransferDataDTO transferDataDTO = new TransferDataDTO();
        transferDataDTO.setAllData(allPowerItem);
        transferDataDTO.setHasData(hasPower);
        return transferDataDTO;
    }

    @Override
    public TransferDataDTO getUserRoleTransferData(String userGuid) throws Exception {
        List<TransferItem> allPowerItem = tbRoleMapper.getAllEnabledRols(IsEnabledEnum.ENABLED.getValue(), IsDeletedEnum.NOTDELETED.getValue());
        List<String> hasPower = trRoleUserMapper.selectRoleGuidListByUserGuid(userGuid,IsEnabledEnum.ENABLED.getStringValue(), IsDeletedEnum.NOTDELETED.getStringValue());
        TransferDataDTO transferDataDTO = new TransferDataDTO();
        transferDataDTO.setAllData(allPowerItem);
        transferDataDTO.setHasData(hasPower);
        return transferDataDTO;
    }

    @Override
    public List<String> selectRoleListByResourceUri(String uri) throws Exception {
        return trRoleResourceMapper.selectRoleListByResourceUri(uri, IsEnabledEnum.ENABLED.getStringValue(), IsDeletedEnum.NOTDELETED.getStringValue());
    }
    @Override
    public List<String> getUserRoleList(String userGuid) throws Exception {
        QueryWrapper<TrRoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_GUID",userGuid)
                .eq("deleted", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("enabled", IsEnabledEnum.ENABLED.getStringValue());
        List<TrRoleUser> trUserRoleList = trRoleUserMapper.selectList(wrapper);
        List list = ReflectionTool.getValuesFromObjectList(trUserRoleList,"roleGuid");
        return list;
    }
}
