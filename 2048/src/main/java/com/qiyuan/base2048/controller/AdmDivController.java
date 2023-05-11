package com.qiyuan.base2048.controller;

import com.qiyuan.base2048.service.manage.AdmDivDataGetter;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-02 11:21
 **/
@RestController
@RequestMapping("/api/system/auth/admdiv")
@ResponseBodyInController
@Slf4j
@Api("字典获取相关接口")
public class AdmDivController {
    @Resource
    private AdmDivDataGetter admDivDataGetter;

    @GetMapping("/lazy/tree/node")
    @ApiOperation("查询行政区划下一层数据")
    public ResultDTO fetchAllAdmDivChildren(@RequestParam("guid") String guid) throws Exception{
        ResultDTO resultDTO = admDivDataGetter.getOneLevelAdmDivNode(guid);
        return resultDTO;
    }
    @GetMapping("/default/checked/keys")
    @ApiOperation("获取通知通告行政区划树缺省勾选的枝叶")
    public ResultDTO fetchDefaultCheckedKeys(@RequestParam("notifyGuid") String notifyGuid) throws Exception {
        ResultDTO resultDTO = admDivDataGetter.getNotifyDefaultCheckedKeys(notifyGuid);
        return resultDTO;
    }
    @GetMapping("/default/expanded/keys")
    @ApiOperation("获取通知通告行政区划树缺省展开的分支")
    public ResultDTO fetchDefaultExpandedKeys(@RequestParam("notifyGuid") String notifyGuid) throws Exception {
        ResultDTO resultDTO = admDivDataGetter.getNotifyDefaultExpandedKeys(notifyGuid);
        return resultDTO;
    }
}
