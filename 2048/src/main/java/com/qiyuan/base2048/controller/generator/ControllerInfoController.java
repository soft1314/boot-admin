package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.service.generator.ControllerInfoGetter;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/generator/controller/info")
@Api("Controller信息操作接口")
public class ControllerInfoController {
    @Autowired
    private ControllerInfoGetter controllerInfoGetter;

    @GetMapping("/list")
    @ApiOperation("获取应用所有Controller信息列表")
    public ResultDTO fetchControllerInfoList() throws Exception{
        return ResultDTO.success(controllerInfoGetter.getAllInfo());
    }

    @GetMapping("/cascaderdata")
    @ApiOperation("获取应用所有Controller信息级联选择器数据")
    public ResultDTO fetchAllCascaderData() throws Exception{
        return ResultDTO.success(controllerInfoGetter.getAllCascaderOptions());
    }
}
