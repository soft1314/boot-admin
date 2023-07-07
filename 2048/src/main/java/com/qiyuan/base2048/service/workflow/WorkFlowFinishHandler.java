package com.qiyuan.base2048.service.workflow;

public interface WorkFlowFinishHandler {
    int pass(String tbName,String guid) throws Exception;
    int destroy(String tbName,String guid) throws Exception;
    int forceDestroy(String tbName,String guid) throws Exception;

}
