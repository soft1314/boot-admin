package com.qiyuan.base2048.service.workflow.impl;

import com.qiyuan.base2048.mapper.mybatis.AnySqlMapper;
import com.qiyuan.base2048.service.workflow.WorkFlowFinishHandler;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.enums.WorkflowStatusEnum;
import com.qiyuan.bautil.util.UserTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkFlowFinishHandlerImpl implements WorkFlowFinishHandler {
    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;
    @Resource
    private AnySqlMapper anySqlMapper;

    @Override
    public int pass(String tbName, String guid) throws Exception {
        return this.update(tbName,guid,WorkflowStatusEnum.PASSED.getValue());
    }

    @Override
    public int destroy(String tbName, String guid) throws Exception {
        return this.update(tbName,guid,WorkflowStatusEnum.DESTROYED.getValue());
    }

    @Override
    public int forceDestroy(String tbName, String guid) throws Exception {
        return this.update(tbName,guid,WorkflowStatusEnum.FORCE.getValue());
    }

    private int update(String tbName, String guid,String workflowStatus) throws Exception{
        BaseUser baseUser = UserTool.getBaseUser();
        String modifyBy = baseUser.getUserName()+"["+baseUser.getLogonName()+"]";

        String sql;
        if(datasourceDriver.contains("oracle")) {
            sql = "update " + tbName + " set flow_status='" + workflowStatus + "',modify_by='"+modifyBy+"',modify_time=sysdate where guid='" + guid + "'";
        }else if(datasourceDriver.contains("mysql")){
            /** mysql 有关时间的，seata rollback 有问题  Class cannot be created (missing no-arg constructor): java.time.LocalDateTime **/
            /** 把业务表相关 DateTime 字段改为 TimeStamp类型，解决 **/
            sql = "update " + tbName + " set flow_status='" + workflowStatus + "',modify_by='"+modifyBy+"',modify_time=now() where guid='" + guid + "'";
//            sql = "update " + tbName + " set flow_status='" + workflowStatus + "',modify_by='"+modifyBy+"' where guid='" + guid + "'";
        }else{
            sql = "update " + tbName + " set flow_status='" + workflowStatus + "',modify_by='"+modifyBy+"',modify_time=sysdate where guid='" + guid + "'";
        }
        /** 字段名不转成大写，SEATA在处理SQL时会抛异常 **/
        sql = sql.toUpperCase();
        int rows = anySqlMapper.superManagerUpdate(sql);
        return rows;
    }
}
