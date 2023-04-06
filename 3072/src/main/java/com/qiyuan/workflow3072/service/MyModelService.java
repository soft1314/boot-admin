package com.qiyuan.workflow3072.service;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.pojo.vo.ModelDataVO;
import com.qiyuan.workflow3072.pojo.vo.ModelQueryVO;

public interface MyModelService {
    ResultDTO getPage(ModelQueryVO queryVO) throws Exception;
    ResultDTO addNewModel(ModelDataVO dataVO, BaseUser baseUser) throws Exception;
    ResultDTO delete(String guid) throws Exception;
    ResultDTO deploy(String guid) throws Exception;
    /**
     * 生成XML
     * 与deploy前半段相同
     */
    ResultDTO genXml(String guid) throws Exception;
}
