package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.vuecomponent.ApiJsGeneratorParameterDTO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.Vue;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.generator.VueApiPostFileGenerator;
import com.qiyuan.base2048.service.generator.VueElementAdminFileGenerator;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@Slf4j
@RequestMapping("/api/system/auth/generator/vueelement")
@Api("Vue Element Admin相关代码生成接口")
public class VueElementAdminGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;
    @Value("${generator.vue.path}")
    private String vueFilePath;
    @Value("${generator.vue.apipath}")
    private String vueApiFilePath;
    @Autowired
    private VueElementAdminFileGenerator vueElementAdminFileGenerator;
    @Autowired
    private VueApiPostFileGenerator vueApiPostFileGenerator;

    @GetMapping("/vuefilepath")
    public ResultDTO getVueFilePath(){
        return ResultDTO.success(vueFilePath);
    }
    @GetMapping("/vueapifilepath")
    public ResultDTO getVueApiFilePath(){
        return ResultDTO.success(vueApiFilePath);
    }
    @PostMapping("/vuefile")
    @ApiOperation("VUE文件生成")
    public ResultDTO createVueElementAdminFile(@Valid @RequestBody Vue vue, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return vueElementAdminFileGenerator.generate(vue);
    }
    @PostMapping("/apijsfile/source")
    @ApiOperation("获取指定API JS文件源码")
    public ResultDTO fetchApiJsSource(@Valid @RequestBody ApiJsGeneratorParameterDTO apiJsGeneratorDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return vueApiPostFileGenerator.getSource(tgProject.getAuthor(),apiJsGeneratorDTO.getControllerName());
    }
    @PostMapping("/apijsfile")
    @ApiOperation("生成一个指定API JS文件")
    public ResultDTO createApiJsFile(@Valid @RequestBody ApiJsGeneratorParameterDTO apiJsGeneratorDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return vueApiPostFileGenerator.generate(tgProject.getApiJsDir(),tgProject.getAuthor(),apiJsGeneratorDTO.getControllerName(),apiJsGeneratorDTO.getOverwrite().booleanValue());
    }

    @PostMapping("/allapijsfile")
    @ApiOperation("生成全部API JS文件")
    public ResultDTO createAllApiJsFile(@Valid @RequestBody ApiJsGeneratorParameterDTO apiJsGeneratorDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return vueApiPostFileGenerator.generateAll(apiJsGeneratorDTO);
    }
}
