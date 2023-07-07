package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.YesNoContainer;
import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.DictypeJsGenerator;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/generator/dictype/js")
@Api("commonDicType代码生成操作接口")
public class DicTypeGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;
    @Resource
    private DictypeJsGenerator dictypeJsGenerator;

    @PostMapping("/code")
    @ApiOperation("生成commonDicType.js源码")
    public ResultDTO generateJsCode() throws Exception{
        TgProject tgProject = projectGetter.getActiveProject();
        ResultDTO resultDTO = dictypeJsGenerator.generateJsCode(tgProject.getAuthor());
        return resultDTO;
    }
    @PostMapping("/file")
    @ApiOperation("生成commonDicType.js文件")
    public ResultDTO generateJsFile(@Valid @RequestBody YesNoContainer overWrite, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        ResultDTO resultDTO = dictypeJsGenerator.generateJsFile(tgProject.getDictypeJsDir(), tgProject.getAuthor(),overWrite.isBoolTrue());
        return resultDTO;
    }
}
