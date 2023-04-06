package com.qiyuan.base2048.service.generator.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.TgFileSummaryMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TgFileSummary;
import com.qiyuan.base2048.service.generator.TxtFileSummaryGetter;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.util.TextFileTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TxtFileSummaryGetterImpl implements TxtFileSummaryGetter {
    @Resource
    private TgFileSummaryMapper tgFileSummaryMapper;
    @Override
    public String fromDb(String fullFileName) throws Exception {
        QueryWrapper<TgFileSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("file_name",fullFileName);
        TgFileSummary tgFileSummary = tgFileSummaryMapper.selectOne(queryWrapper);
        if(tgFileSummary == null){
            return "";
        }
        return tgFileSummary.getSummary();
    }

    @Override
    public String fromIo(String fullFileName) throws Exception {
        return TextFileTool.sm3(fullFileName);
    }
}
