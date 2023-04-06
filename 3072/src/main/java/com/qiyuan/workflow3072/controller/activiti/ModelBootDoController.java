package com.qiyuan.workflow3072.controller.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qiyuan.workflow3072.controller.BaseController;
import com.qiyuan.workflow3072.service.RuntimeDisplayJsonClientResource;
import com.qiyuan.workflow3072.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.qiyuan.workflow3072.constants.ModelDataJsonConstants.*;

/**
 * 本接口由Modeler直接调用
 */
@RequestMapping("/api/workflow/free/activiti")
@RestController
@Slf4j
public class ModelBootDoController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

//    @GetMapping("/model/edit/{id}")
//    public void edit(HttpServletResponse response, @PathVariable("id") String id) {
//        try {
//            response.sendRedirect("/modeler.html?modelId=" + id);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @DeleteMapping("/model/{id}")
//    public R remove(@PathVariable("id") String id) {
//        repositoryService.deleteModel(id);
//        return R.ok();
//    }

    @PostMapping("/model/deploy/{id}")
    public R deploy(@PathVariable("id") String id) throws Exception {
//        //获取模型
//        Model modelData = repositoryService.getModel(id);
//        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
//
//        if (bytes == null) {
//            return R.error("模型数据为空，请先设计流程并成功保存，再进行发布。");
//        }
//
//        JsonNode modelNode = new ObjectMapper().readTree(bytes);
//
//        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
//        if (model.getProcesses().size() == 0) {
//            return R.error("数据模型不符要求，请至少设计一条主线流程。");
//        }
//        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
//
//        //发布流程
//        String processName = modelData.getName() + ".bpmn20.xml";
//        Deployment deployment = repositoryService.createDeployment()
//                .name(modelData.getName())
//                .addString(processName, new String(bpmnBytes, "UTF-8"))
//                .deploy();
//        modelData.setDeploymentId(deployment.getId());
//        repositoryService.saveModel(modelData);

        return R.ok();
    }

    @PostMapping("/model/batchRemove")
    public R batchRemove(@RequestParam("ids[]") String[] ids) {
        for (String id : ids) {
            repositoryService.deleteModel(id);
        }
        return R.ok();
    }


    @GetMapping("/model/export")
    public void exportToXml(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        org.flowable.engine.repository.Model modelData = repositoryService.getModel(id);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
        ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
        IOUtils.copy(in, response.getOutputStream());
        String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.flushBuffer();
    }
    @GetMapping("/model/export1")
    public void exportToXml1(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(id)) {
            return;
        }
        response.setContentType("text/html; charset=UTF-8"); //转码
        try {
            org.flowable.engine.repository.Model modelData = repositoryService.getModel(id);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            byte[] bpmnBytess = new BpmnXMLConverter().convertToXML(bpmnModel);
            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytess);
            String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            IOUtils.copy(in, response.getOutputStream());  //这句必须放到setHeader下面，否则10K以上的xml无法导出，
            response.flushBuffer();
        } catch (IOException e) {
            log.info("导出失败,失败信息为"+e.getMessage());
        }
    }
    /**
     * 获取资源
     * 此接口Free模式保留，暂未找到加header token的方法
     * @return
     */
    @RequestMapping(value = "/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new FlowableException("Error while loading stencil set", e);
        }
    }
}
