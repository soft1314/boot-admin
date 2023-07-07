package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.generator.VueGenerator;
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
@RequestMapping("/api/system/auth/generator/scene2/vue")
@Api("剧情2，树状单表查询、增加、修改、删除等功能 vue 代码生成接口")
public class Scene2VueGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;
    @Resource(name="Scene2VueGeneratorImpl")
    private VueGenerator vueGenerator;

    @PostMapping("/source")
    @ApiOperation("生成剧情2树状单表的基本操作VUE源码")
    public ResultDTO generateSource(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return vueGenerator.generateCode(tableNameContainer.getTableName(),tgProject);
    }
    @PostMapping("/file")
    @ApiOperation("生成剧情2树状单表的基本操作VUE文件")
    public ResultDTO generateFile(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return vueGenerator.generateFile(tableNameContainer.getTableName(),tableNameContainer.isOverwrite(),tgProject);
    }
}
