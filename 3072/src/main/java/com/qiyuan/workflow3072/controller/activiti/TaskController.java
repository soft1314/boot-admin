package com.qiyuan.workflow3072.controller.activiti;

import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.*;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import com.qiyuan.workflow3072.pojo.vo.TaskQueryVO;
import com.qiyuan.workflow3072.service.MyTaskService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/api/workflow/auth/activiti/task")
@RestController
@ResponseBodyInController
@Slf4j
public class TaskController {
    @Autowired
    private MyTaskService myTaskService;
    @Resource
    private FormValidator formValidator;

    /**
     * 获取表单名称
     * @param procDefId
     * @return
     * @throws IOException
     */
    @GetMapping("/formkey")
    public ResultDTO startForm(@RequestParam("procDefId") String procDefId) throws Exception {
        String formKey = myTaskService.getFormKey(procDefId, null);
        if(StringUtils.isNotBlank(formKey)) {
            return ResultDTO.success(formKey);
        }else{
            return ResultDTO.failureCustom("未定义表单！");
        }
    }

    /**
     * 获取当前用户的待办事项
     * @param queryVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/todo")
    public ResultDTO todoPage(@Valid @RequestBody TaskQueryVO queryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser user = UserTool.getBaseUser();
        ResultDTO resultDTO = myTaskService.getTodoPage(queryVO,user);
        return resultDTO;
    }
    /**
     * 获取当前用户的可撤回事项
     * @param queryVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/recall/page")
    public ResultDTO recallPage(@Valid @RequestBody TaskQueryVO queryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser user = UserTool.getBaseUser();
        ResultDTO resultDTO = myTaskService.getRecallPage(queryVO,user);
        return resultDTO;
    }

    /**
     * 管理员获取所有在途事项（所有人的待办）
     * @param queryVO
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/todo/all")
    public ResultDTO allTodoPage(@Valid @RequestBody TaskQueryVO queryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ResultDTO resultDTO = myTaskService.getAllTodoPage(queryVO);
        return resultDTO;
    }

    /**
     * 当前用户的已办任务
     * @param queryVO
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/his")
    public ResultDTO hisTaskList(@Valid @RequestBody TaskQueryVO queryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        ResultDTO resultDTO = myTaskService.getHisPage(queryVO,baseUser);
        return resultDTO;
    }
    @PostMapping("/his/all")
    public ResultDTO hisAllTaskPage(@Valid @RequestBody TaskQueryVO queryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        ResultDTO resultDTO = myTaskService.getAllHisPage(queryVO,baseUser);
        return resultDTO;
    }
    /**
     * 审核，办理
     * @param reviewResultVO
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/review")
    public ResultDTO completeTask(@Valid @RequestBody WorkflowReviewResultVO reviewResultVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser user = UserTool.getBaseUser();
        ResultDTO resultDTO = myTaskService.completeTask(reviewResultVO,user);
        return resultDTO;
    }

    /**
     * 指定流程实例的日志
     * @param guidContainerVO
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/log")
    public ResultDTO fetchLow(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ResultDTO resultDTO = myTaskService.getLog(guidContainerVO.getGuid());
        return resultDTO;
    }
    @GetMapping(value = "/process/diagram")
    @ApiOperation("导出流程图片资源")
    public void genProcessDiagram(@RequestParam("processInstanceId") String processInstanceId, HttpServletResponse httpServletResponse) throws Exception {
        InputStream resourceAsStream = myTaskService.genProcessDiagram(processInstanceId);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            httpServletResponse.getOutputStream().write(b, 0, len);
        }
    }
    /**
     * 读取带跟踪的图片
     */
    @GetMapping(value = "/trace/photo")
    public void tracePhoto(@RequestParam("processInstanceId") String processInstanceId, HttpServletResponse response) throws Exception {
        InputStream inputStream = myTaskService.genTracePhoto(processInstanceId);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
}
