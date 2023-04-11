package com.qiyuan.base2048.service.drools.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.TbDroolsRuleMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDroolsRule;
import com.qiyuan.base2048.service.drools.DroolsRuleGetter;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.util.AdmDivUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DroolsRuleGetterImpl implements DroolsRuleGetter {
    @Resource
    private TbDroolsRuleMapper tbDroolsRuleMapper;

    @Override
    public String getDroolsRuleByName(String name, String admDivCode) throws Exception{
        QueryWrapper<TbDroolsRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NAME",name);
        queryWrapper.eq("ENABLED", IsEnabledEnum.ENABLED.getValue().toString());
        String[] mofDivCodeArray = AdmDivUtil.getUpMofDivCodeArray(admDivCode);
        if(mofDivCodeArray.length == 1 && "".equalsIgnoreCase(mofDivCodeArray[0])){
            queryWrapper.eq("ADM_DIV_CODE", ConstantCommon.PARAMETER_NON);
        }else {
            queryWrapper.in("ADM_DIV_CODE", mofDivCodeArray);
        }
        queryWrapper.orderByDesc("ADM_DIV_CODE");
        List<TbDroolsRule> gpDroolsRuleList = tbDroolsRuleMapper.selectList(queryWrapper);
        if(gpDroolsRuleList == null || gpDroolsRuleList.size() == 0){
            return "";
        }
        return gpDroolsRuleList.get(0).getRule();
    }
}
