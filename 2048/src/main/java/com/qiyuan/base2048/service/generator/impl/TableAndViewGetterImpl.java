package com.qiyuan.base2048.service.generator.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.VTablesMasterMapper;
import com.qiyuan.base2048.pojo.vo.vuecomponent.TableFilterDTO;
import com.qiyuan.base2048.service.generator.TableAndViewGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TableAndViewGetterImpl implements TableAndViewGetter {
    @Value("${base2048.table-schema}")
    private String tableSchema;

    @Resource
    private VTablesMasterMapper vTablesMasterMapper;

    @Override
    public ResultDTO getPage(TableFilterDTO tableFilterDTO) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("TABLE_SCHEMA",tableSchema);
        if(StringUtils.isNotBlank(tableFilterDTO.getTableName())){
            queryWrapper.like("TABLENAME",tableFilterDTO.getTableName());
        }
        if(StringUtils.isNotBlank(tableFilterDTO.getTableType())){
            queryWrapper.eq("TABLETYPE",tableFilterDTO.getTableType());
        }
        if(StringUtils.isNotBlank(tableFilterDTO.getTableComments())){
            queryWrapper.like("COMMENTS",tableFilterDTO.getTableComments());
        }

        IPage iPage = new Page(tableFilterDTO.getCurrentPage(), tableFilterDTO.getPageSize());
        if("master".equalsIgnoreCase(tableFilterDTO.getDataSource())){
            return ResultDTO.success(vTablesMasterMapper.selectPage(iPage,queryWrapper));
        }else{
            return ResultDTO.failureCustom("不支持的数据源。");
        }
    }
}
