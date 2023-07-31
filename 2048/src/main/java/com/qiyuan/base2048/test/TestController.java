package com.qiyuan.base2048.test;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.ResponseExcel;
import com.qiyuan.base2048.pojo.TestDTO;
import com.qiyuan.base2048.pojo.dto.workflow.ProcessDefinitionDTO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/system/free/test")
public class TestController {
    @Resource
    private FormValidator formValidator;

    @GetMapping("/test1")
    public ResultDTO test1(HttpServletRequest request) throws Exception {
        String uaStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentParser.parse(uaStr);

        System.out.println(userAgent.getBrowser());
        System.out.println(userAgent.getVersion());

        System.out.println(userAgent.getOs());
        System.out.println(userAgent.getOsVersion());

        System.out.println(userAgent.getEngine());
        System.out.println(userAgent.getEngineVersion());

        System.out.println(userAgent.getPlatform());

        return ResultDTO.success();
    }

    @PostMapping("/test2")
    public ResultDTO test2(@Valid @RequestBody TestDTO testDTO, BindingResult bindingResult, HttpServletRequest request) throws Exception {
        /** 表单对象 VO 验证 **/
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return ResultDTO.success(testDTO);
    }

    @GetMapping("/test3")
    public TestDTO get() {
        TestDTO testDTO = new TestDTO();
        testDTO.setName("张三丰");
        testDTO.setFirstName("张");
        testDTO.setAge(66);
        testDTO.setBirth(new Date());
        testDTO.setInterest(new ArrayList<>());
        testDTO.setSex("1");
        testDTO.getInterest().add("abc");
        testDTO.getInterest().add("ddd");
        return testDTO;
    }

    @GetMapping("/test4")
    public String test4(@RequestParam("level") int level, @RequestParam("msg") String msg) {
        switch (level) {
            case 1:
                log.trace("这是trace消息：{}", msg);
                break;
            case 2:
                log.debug("这是debug消息：{}", msg);
                break;
            case 3:
                log.info("这是info消息：{}", msg);
                break;
            case 4:
                log.warn("这是warn消息：{}", msg);
                break;
            case 5:
                log.error("这是error消息：{}", msg);
                break;
            default:
                log.error("这是什么消息：{}", msg);
        }
        return "消息已打印";
    }

    @GetMapping("/testexcel")
    @ResponseExcel(fileName="Java知识日历20201101测试",sheetName = "同一班的同学名册",columnNames= {"学生姓名","学号","年龄"},classFieldNames = { "name","key","version" })
    public List<ProcessDefinitionDTO> testexcel(HttpServletRequest request) throws Exception {
        List<ProcessDefinitionDTO> ret = new ArrayList<>();
        ProcessDefinitionDTO processDefinitionDTO = new ProcessDefinitionDTO();
        processDefinitionDTO.setName("李四");
        processDefinitionDTO.setKey("0001");
        processDefinitionDTO.setVersion(BigDecimal.TEN);
        processDefinitionDTO = new ProcessDefinitionDTO();
        processDefinitionDTO.setName("张三");
        processDefinitionDTO.setKey("0002");
        processDefinitionDTO.setVersion(BigDecimal.ONE);
        ret.add(processDefinitionDTO);
        return ret;
    }
}
