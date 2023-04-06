package com.qiyuan.base2048.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.mapper.mapstruct.JobDtoTransMapper;
import com.qiyuan.bautil.dto.BasePageQueryVO;
import com.qiyuan.base2048.quartz.QuartzService;
import com.qiyuan.base2048.quartz.dto.JobDTO;
import com.qiyuan.base2048.quartz.dto.JobUpdateDTO;
import com.qiyuan.base2048.quartz.job.BaseJob;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/job")
@Slf4j
public class QuartzJobController {
    @Resource
    private QuartzService quartzService;

    @PostMapping("/save")
    @ApiOperation(value = "保存添加或修改任务",notes = "保存添加或修改任务")
    public ResultDTO addOrUpdate(@RequestBody JobUpdateDTO jobUpdateDTO) throws Exception {
        if (StringUtils.isBlank(jobUpdateDTO.getOldJobName())) {
            ResultDTO resultDTO = this.addSave(jobUpdateDTO);
            return resultDTO;
        } else {
            /**
             * 先删除后添加
             */
            JobDTO jobDTO = new JobDTO();
            jobDTO.setJobName(jobUpdateDTO.getOldJobName());
            jobDTO.setJobGroup(jobUpdateDTO.getOldJobGroup());
            this.delete(jobDTO);
            ResultDTO resultDTO = this.addSave(jobUpdateDTO);
            return resultDTO;
        }
    }
    private ResultDTO addSave(@RequestBody JobUpdateDTO jobUpdateDTO) throws Exception {
        BaseJob job = (BaseJob) Class.forName(jobUpdateDTO.getJobClassName()).newInstance();
        job.setJobName(jobUpdateDTO.getJobName());
        job.setJobGroup(jobUpdateDTO.getJobGroup());
        job.setDescription(jobUpdateDTO.getDescription());
        job.setCronExpression(jobUpdateDTO.getCronExpression());
        try {
            quartzService.addJob(job);
            return  ResultDTO.success();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return ResultDTO.failureCustom("保存添加任务时服务发生意外情况。");
        }
    }
    @PostMapping("/page")
    @ApiOperation(value = "查询任务",notes = "查询任务")
    public ResultDTO getJobPage(@RequestBody BasePageQueryVO basePageQueryVO) {
        try {
            IPage<JobDTO> jobDtoPage = quartzService.queryJob(basePageQueryVO.getCurrentPage(),basePageQueryVO.getPageSize());
            return  ResultDTO.success(jobDtoPage);
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return ResultDTO.failureCustom("查询任务时服务发生意外情况。");
        }
    }
    @PostMapping("/pause")
    @ApiOperation(value = "暂停任务",notes = "暂停任务")
    public ResultDTO pause(@RequestBody JobDTO jobDTO) {
        try {
            quartzService.pauseJob(jobDTO.getJobName(),jobDTO.getJobGroup());
            return ResultDTO.success();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return ResultDTO.failureCustom("暂停任务时服务发生意外情况。");
        }
    }

    @PostMapping("/resume")
    @ApiOperation(value = "恢复任务",notes = "恢复任务")
    public ResultDTO resume(@RequestBody JobDTO jobDTO) {
        try {
            quartzService.resumeJob(jobDTO.getJobName(),jobDTO.getJobGroup());
            return ResultDTO.success();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return ResultDTO.failureCustom("恢复任务时服务发生意外情况。");
        }
    }
    @PostMapping("/delete")
    @ApiOperation(value = "删除任务",notes = "删除任务")
    public ResultDTO delete(@RequestBody JobDTO jobDTO) {
        try {
            /**
             * 注：这里返回false，可是成功删除了，所以先反着写，目前还不知道何时返回true
             */
            if(quartzService.deleteJob(jobDTO.getJobName(),jobDTO.getJobGroup())) {
                return ResultDTO.failureCustom("删除失败。");
            }else{
                return ResultDTO.success();
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return ResultDTO.failureCustom("删除任务时服务发生意外情况。");
        }
    }
    @GetMapping("/blank")
    public ResultDTO getBlankJobDTO(){
        JobUpdateDTO jobUpdateDTO = new JobUpdateDTO();
        jobUpdateDTO.setJobClassName("com.qiyuan.base2048.quartz.job.jobs.");
        jobUpdateDTO.setCronExpression("*/9 * * * * ?");
        return ResultDTO.success(jobUpdateDTO);
    }

    @PostMapping("/dataforupdate")
    public ResultDTO getUpdateJobDTO(@RequestBody JobDTO jobDTO){
        JobUpdateDTO jobUpdateDTO = JobDtoTransMapper.INSTANCE.map(jobDTO);
        jobUpdateDTO.setOldJobName(jobDTO.getJobName());
        jobUpdateDTO.setOldJobGroup(jobDTO.getJobGroup());
        return ResultDTO.success(jobUpdateDTO);
    }
}
