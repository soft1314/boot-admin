package com.qiyuan.workflow3072.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.pojo.vo.ProcessDataVO;
import com.qiyuan.workflow3072.pojo.vo.ProcessQueryVO;
import com.qiyuan.workflow3072.service.MyProcessService;
import com.qiyuan.workflow3072.service.transaction.GlobalTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class MyProcessServiceImpl implements MyProcessService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private GlobalTransaction globalTransaction;

    @Override
    public ResultDTO getPage(ProcessQueryVO queryVO,boolean lastVersion) throws Exception {
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        int limit = queryVO.getPageSize();

        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if (lastVersion) {
            query.latestVersion();
        }
        List<ProcessDefinition> processDefinitions = query
                .orderByProcessDefinitionKey().asc()
                .orderByProcessDefinitionVersion().desc()
                .listPage(offset, limit);
        int total = (int) repositoryService.createProcessDefinitionQuery().count();
        List<ProcessDataVO> list = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            ProcessDataVO processDataVO = new ProcessDataVO();
            processDataVO.setId(processDefinition.getId());
            processDataVO.setDeploymentId(processDefinition.getDeploymentId());
            processDataVO.setName(processDefinition.getName());
            processDataVO.setCategory(processDefinition.getCategory());
            processDataVO.setKey(processDefinition.getKey());
            processDataVO.setDescription(processDefinition.getDescription());
            processDataVO.setVersion(processDefinition.getVersion());

            list.add(processDataVO);
        }
        Page<ProcessDataVO> page = new Page<>();
        page.setRecords(list);
        page.setTotal(total);
        return ResultDTO.success(page);
    }

    @Override
    public ResultDTO addNewProcess(String category, MultipartFile file) throws Exception {
        String message = "";
        String fileName = file.getOriginalFilename();
        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else if (extension.equals("png")) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (fileName.indexOf("bpmn20.xml") != -1) {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            } else if (extension.equals("bpmn")) {
                /**  bpmn扩展名特殊处理，转换为bpmn20.xml **/
                String baseName = FilenameUtils.getBaseName(fileName);
                deployment = repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
            } else {
                message = "不支持的文件类型：" + extension;
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                message += "部署成功，流程ID=" + processDefinition.getId() + "<br/>";
            }

            if (list.size() == 0) {
                message = "部署失败，没有流程。";
            }

        } catch (Exception e) {
            throw new FlowableException("部署失败！", e);
        }
        return ResultDTO.success(message);
    }

    @Override
    public ResultDTO delete(String guid) throws Exception {
        repositoryService.deleteDeployment(guid,true);
        return ResultDTO.success();
    }

    @Override
    public ResultDTO deploy(String guid) throws Exception {
        Model modelData = convertToModel(guid);
        return ResultDTO.success("转换模型成功，模型ID=" + modelData.getId());
    }

    @Override
    public Model convertToModel(String procDefId) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        org.flowable.engine.repository.Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
//        modelData.setName(processDefinition.getResourceName());
        modelData.setName(processDefinition.getName());

        modelData.setCategory(processDefinition.getCategory());//.getDeploymentId());
        modelData.setDeploymentId(processDefinition.getDeploymentId());
        modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count() + 1)));
        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put("name", processDefinition.getName());
        modelObjectNode.put("revision", modelData.getVersion());
        modelObjectNode.put("description", processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
        return modelData;
    }

    @Override
    public InputStream resourceRead(String id, String resType) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
        String resourceName = "";
        if (resType.equals("image/png")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resType.equals("text/xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        return resourceAsStream;
    }

    @Override
    public ResultDTO stopProcessInstanceById(String processInstanceId,String taskId, String businessTable, String businessId) throws Exception {
        /** 全局事务  **/
        ResultDTO resultDTO = globalTransaction.stopProcessInstanceById(processInstanceId,taskId,businessTable,businessId);
        return resultDTO;
    }




}
