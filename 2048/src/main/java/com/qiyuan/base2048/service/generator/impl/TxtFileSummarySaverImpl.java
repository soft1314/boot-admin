package com.qiyuan.base2048.service.generator.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TgFileSummaryMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TgFileSummary;
import com.qiyuan.base2048.service.generator.TxtFileSummarySaver;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TxtFileSummarySaverImpl implements TxtFileSummarySaver {
    @Resource
    private TgFileSummaryMapper tgFileSummaryMapper;

    @Override
    public int save(String fullFileName, String summary) throws Exception {
        UpdateWrapper<TgFileSummary> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("enabled", IsEnabledEnum.ENABLED.getStringValue())
                .eq("deleted", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("file_name", fullFileName)
                .set("enabled", IsEnabledEnum.NOTENABLED.getStringValue())
                .set("modify_by", "admin")
                .set("modify_time", new Date());
        tgFileSummaryMapper.update(null, updateWrapper);

        TgFileSummary tgFileSummary = new TgFileSummary();
        tgFileSummary.setFileName(fullFileName);
        tgFileSummary.setSummary(summary);
        Date now = new Date();
        tgFileSummary.setCreateBy("admin");
        tgFileSummary.setCreateTime(now);
        tgFileSummary.setModifyBy("admin");
        tgFileSummary.setModifyTime(now);
        tgFileSummary.setDatestamp(now);
        tgFileSummary.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        tgFileSummary.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        return tgFileSummaryMapper.insert(tgFileSummary);
    }

    @Override
    public int backup(String fullFileName, String bakFileName) {
        UpdateWrapper<TgFileSummary> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("enabled", IsEnabledEnum.ENABLED.getStringValue())
                .eq("deleted", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("file_name", fullFileName)
                .set("file_name", bakFileName)
                .set("modify_by", "admin")
                .set("modify_time", new Date());
        return tgFileSummaryMapper.update(null, updateWrapper);
    }
}
