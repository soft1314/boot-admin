package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.LiquibaseChangeLogGenerator;
import com.qiyuan.base2048.service.generator.ProjectGetter;
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
@RequestMapping("/api/system/auth/generator/scene3/liquibase")
@Api("剧情3，ChangeLog 代码生成接口")
public class Scene3LiquibaseChangeLogGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;

    @Resource(name="Scene3LiquibaseChangeLogGeneratorImpl")
    private LiquibaseChangeLogGenerator liquibaseChangeLogGenerator;

    @PostMapping("/source")
    @ApiOperation("生成ChangeLog源码")
    public ResultDTO generateSingle1Source(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return liquibaseChangeLogGenerator.generateCode(tableNameContainer.getTableName(),tgProject);
    }
    @PostMapping("/file")
    @ApiOperation("生成ChangeLog源码文件")
    public ResultDTO generateSingle1File(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return liquibaseChangeLogGenerator.generateFile(tableNameContainer.getTableName(),tgProject);
    }
}
