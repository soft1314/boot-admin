package com.qiyuan.workflow3072.controller.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.qiyuan.bautil.dto.*;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import com.qiyuan.workflow3072.service.MyTaskService;
import com.qiyuan.workflow3072.service.RuntimeDisplayJsonClientResource;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/api/workflow/auth/activiti/task")
@RestController
@Slf4j
public class TaskReturnResultDTOController {
    @Autowired
    private MyTaskService myTaskService;
    @Resource
    private FormValidator formValidator;
    @Autowired
    private RuntimeDisplayJsonClientResource runtimeDisplayJsonClientResource;
    /**
     * 启动流程
     * @param startProcessDTO
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/process/start")
    public ResultDTO startProcess(@Valid @RequestBody WorkflowStartProcessDTO startProcessDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return myTaskService.startProcess(startProcessDTO);
    }

    @PostMapping("/business/vars")
    @ApiOperation("通过任务ID获取业务数据")
    public ResultDTO<WorkflowBusinessDataVO> getByTaskId(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return myTaskService.getOneByTaskId(guidContainerVO.getGuid());
    }

    /**
     * 审核，办理
     * @param reviewResultVO
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/complete")
    public ResultDTO review(@Valid @RequestBody WorkflowReviewResultVO reviewResultVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser user = UserTool.getBaseUser();
        ResultDTO resultDTO = myTaskService.completeTask(reviewResultVO,user);
        return resultDTO;
    }

    @GetMapping(value = "/process/instances")
    public JsonNode getModelJSON(@RequestParam("processInstanceId") String processInstanceId) {
        try {
            JsonNode jsonNode = runtimeDisplayJsonClientResource.getModelJSON(processInstanceId);
            return jsonNode;
        }catch (Exception ex){
            return null;
        }
    }
}
