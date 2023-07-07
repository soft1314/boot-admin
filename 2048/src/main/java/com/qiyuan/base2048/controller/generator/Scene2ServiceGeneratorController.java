package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.generator.ServiceGenerator;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@Slf4j
@RequestMapping("/api/system/auth/generator/scene2/service")
@Api("剧情1，Service 代码生成接口")
public class Scene2ServiceGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;
    @Resource(name="Scene2ServiceGeneratorImpl")
    private ServiceGenerator serviceGenerator;

    @PostMapping("/source")
    @ApiOperation("生成对VO源码")
    public ResultDTO generateSingle1Source(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return serviceGenerator.generateCode(tableNameContainer.getTableName(),tgProject);
    }
    @PostMapping("/file")
    @ApiOperation("生成VO文件")
    public ResultDTO generateSingle1File(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return serviceGenerator.generateFile(tableNameContainer.getTableName(),tgProject,tableNameContainer.isOverwrite());
    }
}
