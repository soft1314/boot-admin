package com.qiyuan.base2048.service.workflow;

import java.util.Map;

public interface ProcDefKeySelector {
    /**
     * 利用DROOLS计算使用哪一条流程，返回流程定义ID
     * @param businessTable 表名，用于确定哪条规则
     * @param map 参数，与规则相配
     * @return
     * @throws Exception
     */
    String selectProcDefKey(String businessTable, Map map) throws Exception;
}
