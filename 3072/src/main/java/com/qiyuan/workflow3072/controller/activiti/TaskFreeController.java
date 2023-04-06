package com.qiyuan.workflow3072.controller.activiti;

import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.workflow3072.service.MyTaskService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RequestMapping("/api/workflow/free/activiti/task")
@RestController
@ResponseBodyInController
@Slf4j
public class TaskFreeController {
    @Autowired
    private MyTaskService myTaskService;





}
