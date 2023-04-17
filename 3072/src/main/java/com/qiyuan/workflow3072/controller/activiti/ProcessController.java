package com.qiyuan.workflow3072.controller.activiti;

import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.workflow3072.controller.BaseController;
import com.qiyuan.workflow3072.pojo.vo.ProcessQueryVO;
import com.qiyuan.workflow3072.service.MyProcessService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;

@RequestMapping("/api/workflow/auth/activiti")
@RestController
@ResponseBodyInController
@Slf4j
public class ProcessController extends BaseController {
    @Resource
    private FormValidator formValidator;
    @Autowired
    private MyProcessService processService;

    @PostMapping("/process/page")
    @ApiOperation("分页获取流程（最新版本）数据表列表")
    public ResultDTO getTablePage(@Valid @RequestBody ProcessQueryVO queryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ResultDTO resultDTO = processService.getPage(queryVO,true);
        return resultDTO;
    }
    @PostMapping("/process/page/all")
    @ApiOperation("分页获取流程（所有版本）数据表列表")
    public ResultDTO getAllTablePage(@Valid @RequestBody ProcessQueryVO queryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ResultDTO resultDTO = processService.getPage(queryVO,false);
        return resultDTO;
    }
    @PostMapping("/process/del")
    @ApiOperation("删除数据")
    public ResultDTO del(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return processService.delete(guidContainerVO.getGuid());
    }

    @PostMapping("/process/deploy")
    @ApiOperation("流程转模型")
    public ResultDTO deploy(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return processService.deploy(guidContainerVO.getGuid());
    }

    /**
     * 通过processDefinition.id和resType导出流程XML或图片资源
     * @param id processDefinition.id
     * @param resType 取值 “image/png”或“text/xml”
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/res/exp")
    @ApiOperation("通过processDefinition.id和resType导出流程XML或图片资源")
    public void resourceRead(@RequestParam("id") String id,@RequestParam("resType") String resType, HttpServletResponse response) throws Exception {
        /** resType取值 “image/png”或“text/xml” **/
        InputStream resourceAsStream = processService.resourceRead(id,resType);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    @GetMapping(value = "/process/destroy")
    @ApiOperation("强行终止流程")
    public ResultDTO stopProcessInstanceById(@RequestParam("processInstanceId") String processInstanceId,@RequestParam("taskId") String taskId,@RequestParam("businessTable") String businessTable,@RequestParam("businessId") String businessId) throws Exception{
        ResultDTO resultDTO = processService.stopProcessInstanceById(processInstanceId,taskId,businessTable,businessId);
        return resultDTO;
    }

}
