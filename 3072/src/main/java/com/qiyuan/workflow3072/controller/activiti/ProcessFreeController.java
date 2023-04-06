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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;

@RequestMapping("/api/workflow/free/activiti")
@RestController
@ResponseBodyInController
@Slf4j
public class ProcessFreeController extends BaseController {
    @Autowired
    private MyProcessService processService;

    @PostMapping("/process/add")
    @ApiOperation("保存数据")
    public ResultDTO save(@RequestParam("file") MultipartFile file,@RequestParam("processCategory") String processCategory) throws Exception {
        return processService.addNewProcess(processCategory,file);
    }




}
