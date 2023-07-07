package com.qiyuan.base2048.service.drools;

import com.qiyuan.bautil.dto.ResultDTO;

public interface DroolsLauncher {
    /**
     * 执行获取结果类的规则，缺省失败，匹配规则后成功
     */
    ResultDTO fireFetch(Object mainObj, Object extraObj,String rule) throws Exception;
    /**
     * 执行判定类的规则，缺省通过，匹配规则后结论为未通过
     */
    ResultDTO fireJudge(Object mainObj, Object extraObj,String rule) throws Exception;
}
