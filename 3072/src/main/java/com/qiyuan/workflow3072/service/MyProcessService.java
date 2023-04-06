package com.qiyuan.workflow3072.service;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.pojo.vo.ProcessDataVO;
import com.qiyuan.workflow3072.pojo.vo.ProcessQueryVO;
import org.flowable.engine.repository.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MyProcessService {
    ResultDTO getPage(ProcessQueryVO queryVO,boolean lastVersion) throws Exception;
    ResultDTO addNewProcess(String category, MultipartFile file) throws Exception;
    ResultDTO delete(String guid) throws Exception;
    ResultDTO deploy(String guid) throws Exception;

    Model convertToModel(String procDefId) throws Exception;
    InputStream resourceRead(String id, String resType) throws Exception;

    ResultDTO stopProcessInstanceById(String processInstanceId,String taskId,String businessTable,String businessId) throws Exception;
}
