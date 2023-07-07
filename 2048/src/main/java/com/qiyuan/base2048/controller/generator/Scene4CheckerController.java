package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.base2048.service.generator.TableChecker;
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
@RequestMapping("/api/system/auth/generator/scene4/check")
@Api("剧情1，规范检查")
public class Scene4CheckerController {
    @Resource
    private FormValidator formValidator;
    @Resource(name="Scene4TableCheckerImpl")
    private TableChecker tableChecker;
    @Resource(name="Scene4StoryGeneratorImpl")
    private StoryGenerator storyGenerator;
    @PostMapping("/table")
    @ApiOperation("对数据表规范检查")
    public ResultDTO generateScene4TableCheckResult(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return tableChecker.checkTable(tableNameContainer.getTableName());
    }
    @PostMapping("/story")
    @ApiOperation("剧本4剧情")
    public ResultDTO generateScene4Story(@Valid @RequestBody TableNameContainer tableNameContainer, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return storyGenerator.story(tableNameContainer.getTableName());
    }
}
