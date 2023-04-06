package com.qiyuan.base2048.service.generator.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TgProjectMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generator.ProjectSaver;
import com.qiyuan.base2048.service.mybatis.ITgProjectService;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.enums.YesNoIntegerEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectSaverImpl implements ProjectSaver {
    @Resource
    private TgProjectMapper tgProjectMapper;
    @Resource
    private ITgProjectService tgProjectService;

    @Override
    public ResultDTO insertOrUpdateTgProject(TgProject tgProject, BaseUser baseUser) throws Exception {
        int row=0;
        if(StringUtils.isBlank(tgProject.getGuid())){
            tgProject = setInitialValueWhenInsert(tgProject,baseUser);
            row = tgProjectMapper.insert(tgProject);
        }else{
            row = tgProjectMapper.updateById(tgProject);
        }
        if(row == 1){
            return ResultDTO.successCustom(MessageUtils.get("tg-project.save.success"), null);
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("tg-project.save.error"));
        }
    }

    @Override
    public ResultDTO activeTgProject(String guid) throws Exception {
        UpdateWrapper<TgProject> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("default_proj",YesNoEnum.YES.getValue())
                .eq("deleted",IsDeletedEnum.NOTDELETED.getBigDecimalValue())
                .set("default_proj", YesNoEnum.NO.getValue());
        tgProjectService.update(updateWrapper);

        UpdateWrapper<TgProject> updateWrapper1 = new UpdateWrapper();
        updateWrapper1.eq("guid",guid)
                .eq("deleted",IsDeletedEnum.NOTDELETED.getBigDecimalValue())
                .set("default_proj", YesNoEnum.YES.getValue())
                .set("enabled",IsEnabledEnum.ENABLED.getBigDecimalValue());
        if(tgProjectService.update(updateWrapper1)){
            return ResultDTO.success();
        }
        return ResultDTO.failureCustom(MessageUtils.get("dao.update.error"));
    }

    @Override
    public ResultDTO deleteTgProject(String guid) throws Exception {
        UpdateWrapper<TgProject> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
        if(tgProjectService.update(updateWrapper)){
            return ResultDTO.success();
        }
        return ResultDTO.failureCustom(MessageUtils.get("dao.delete.error"));
    }

    private TgProject setInitialValueWhenInsert(TgProject tgProject, BaseUser baseUser) throws Exception{
        tgProject.setDefaultProj(YesNoEnum.NO.getValue());
        tgProject.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        tgProject.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        return tgProject;
    }
}
