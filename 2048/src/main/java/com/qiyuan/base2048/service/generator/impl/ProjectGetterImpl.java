package com.qiyuan.base2048.service.generator.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.query.ProjectQueryVO;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.mybatis.ITgProjectService;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static cn.hutool.core.bean.BeanUtil.beanToMap;

@Service
public class ProjectGetterImpl implements ProjectGetter {
    @Resource
    private ITgProjectService iTgProjectService;

    @Override
    public ResultDTO getPage(ProjectQueryVO projectQueryVO,boolean includeDeleted) throws Exception {
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
        tgProjectPage = iTgProjectService.page(tgProjectPage,wrapper);

        return ResultDTO.success(tgProjectPage);
    }

    @Override
    public TgProject getActiveProject() throws Exception {
        QueryWrapper<TgProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DEFAULT_PROJ",YesNoEnum.YES.getValue())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getBigDecimalValue())
                .eq("DELETED",IsDeletedEnum.NOTDELETED.getBigDecimalValue());
        TgProject tgProject = iTgProjectService.getOne(queryWrapper);
        return tgProject;
    }
}
