package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.TgProjectMapper;
import com.qiyuan.base2048.pojo.vo.query.ProjectQueryVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.mybatis.ITgProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.StringTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static cn.hutool.core.bean.BeanUtil.beanToMap;

/**
* <p>
* 代码生成功能项目定义表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-6-17 10:42:14
*/
@Service
public class TgProjectServiceImpl extends ServiceImpl<TgProjectMapper, TgProject> implements ITgProjectService {
    @Override
    public ResultDTO getPage(ProjectQueryVO projectQueryVO, boolean includeDeleted) throws Exception {
        QueryWrapper<TgProject> wrapper = new QueryWrapper();
        if(!includeDeleted) {
            wrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getValue());
        }

        Map<String, Object> voMap = beanToMap(projectQueryVO, false, true);
        for (String key : voMap.keySet()) {
            if("currentPage".equalsIgnoreCase(key)){
                continue;
            }
            if("pageSize".equalsIgnoreCase(key)){
                continue;
            }
            /** todo 还有其他数据类型  **/
            String fieldName = StringTool.humpToLine(key);
            if("java.lang.Integer".equalsIgnoreCase(voMap.get(key).getClass().getName())){
                Integer integerValud = (Integer) voMap.get(key);
                wrapper.eq(fieldName, integerValud);
            }else if("java.lang.String".equalsIgnoreCase(voMap.get(key).getClass().getName())){
                String stringValud = voMap.get(key).toString();
                wrapper.like(fieldName, stringValud);
            }
            System.out.println(voMap.get(key).getClass().getName());
        }


        IPage<TgProject> tgProjectPage = new Page<>(projectQueryVO.getCurrentPage(),projectQueryVO.getPageSize());
        tgProjectPage = this.page(tgProjectPage,wrapper);

        return ResultDTO.success(tgProjectPage);
    }
    @Override
    public ResultDTO insertOrUpdateTgProject(TgProject tgProject, BaseUser baseUser) throws Exception {
        boolean result;
        if(StringUtils.isBlank(tgProject.getGuid())){
            tgProject = setInitialValueWhenInsert(tgProject,baseUser);
            result = this.save(tgProject);
        }else{
            result = this.updateById(tgProject);
        }
        if(result){
            return ResultDTO.successCustom(MessageUtils.get("tg-project.save.success"), null);
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("tg-project.save.error"));
        }
    }

    @Override
    public ResultDTO deleteTgProject(String guid) throws Exception {
        UpdateWrapper<TgProject> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
        if(this.update(updateWrapper)){
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