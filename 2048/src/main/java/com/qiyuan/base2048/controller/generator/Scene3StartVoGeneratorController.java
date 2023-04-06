package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.generator.VoGenerator;
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
@RequestMapping("/api/system/auth/generator/scene3/vo/start")
@Api("剧情3，启动流程用vo 代码生成接口")
public class Scene3StartVoGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;
    @Resource(name="Scene3StartVoGeneratorImpl")
    private VoGenerator voGenerator;

    @PostMapping("/source")
    @ApiOperation("生成对VO源码")
    public ResultDTO generateSource(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return voGenerator.generateCode(tableNameContainer.getTableName(),tgProject.getPackageVo(),generatorInfoAdapter.getVoFilePath(tgProject),tgProject.getAuthor());
    }
    @PostMapping("/file")
    @ApiOperation("生成VO文件")
    public ResultDTO generateFile(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return voGenerator.generateFile(tableNameContainer.getTableName(),tgProject.getPackageVo(),generatorInfoAdapter.getVoFilePath(tgProject),tgProject.getAuthor(),tableNameContainer.isOverwrite());
    }
}
