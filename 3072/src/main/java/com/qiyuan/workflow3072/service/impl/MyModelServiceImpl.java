package com.qiyuan.workflow3072.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.workflow3072.constants.ModelDataJsonConstants;
import com.qiyuan.workflow3072.pojo.vo.ModelDataVO;
import com.qiyuan.workflow3072.pojo.vo.ModelQueryVO;
import com.qiyuan.workflow3072.service.MyModelService;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.qiyuan.workflow3072.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static com.qiyuan.workflow3072.constants.ModelDataJsonConstants.MODEL_NAME;

@Service
public class MyModelServiceImpl implements MyModelService {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResultDTO getPage(ModelQueryVO queryVO) throws Exception {
        int offset = (queryVO.getCurrentPage() - 1) * queryVO.getPageSize() + 0;
        List<Model> list = repositoryService.createModelQuery().listPage(offset
                , queryVO.getPageSize());
        int total = (int) repositoryService.createModelQuery().count();
        Page<Model> page = new Page<>();
        page.setRecords(list);
        page.setTotal(total);
        return ResultDTO.success(page);
    }

    @Override
    public ResultDTO addNewModel(ModelDataVO dataVO, BaseUser baseUser) throws Exception {
        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        String name = dataVO.getName();
        String description = dataVO.getDescription();
        int revision = 1;
        String key = dataVO.getKey();

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(MODEL_NAME, name);
        modelNode.put(MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        return ResultDTO.success(id);
    }

    @Override
    public ResultDTO delete(String guid) throws Exception {
        repositoryService.deleteModel(guid);
        return ResultDTO.success();
    }

    @Override
    public ResultDTO deploy(String guid) throws Exception {
        /**获取模型 **/
        Model modelData = repositoryService.getModel(guid);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return ResultDTO.failureCustom("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            return ResultDTO.failureCustom("数据模型不符要求，请至少设计一条主线流程。");
        }

        /** 这里设置名称 **/
//        model.getMainProcess().setName(modelData.getName());
        if(StringUtils.isNotBlank(modelData.getCategory())) {
            model.setTargetNamespace(modelData.getCategory());
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        /** 发布流程 .bpmn20.xml必需加 **/
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .category(modelData.getCategory())
                .key(modelData.getKey())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return ResultDTO.success();
    }

    @Override
    public ResultDTO genXml(String guid) throws Exception {
        /**获取模型 **/
        Model modelData = repositoryService.getModel(guid);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return ResultDTO.failureCustom("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            return ResultDTO.failureCustom("数据模型不符要求，请至少设计一条主线流程。");
        }

        /** 这里设置名称 **/
        model.getMainProcess().setName(modelData.getName());
        if(StringUtils.isNotBlank(modelData.getCategory())) {
            model.setTargetNamespace(modelData.getCategory());
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        String xml = new String(bpmnBytes, "UTF-8");
        return ResultDTO.success(xml);
    }
}
