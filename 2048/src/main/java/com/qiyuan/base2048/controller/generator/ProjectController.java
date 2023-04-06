package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mapstruct.TgProjectStructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.eltable.TgProjectVO;
import com.qiyuan.base2048.pojo.vo.query.ProjectQueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.generator.ProjectSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
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
@RequestMapping("/api/system/auth/generator/project")
@Api("代码生成操作项目管理")
public class ProjectController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ProjectGetter projectGetter;
    @Resource
    private ProjectSaver projectSaver;

    @PostMapping("/table/page")
    @ApiOperation("分页获取项目数据表列表")
    public ResultDTO getTablePage(@Valid @RequestBody ProjectQueryVO projectQueryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return projectGetter.getPage(projectQueryVO,false);
    }
    @PostMapping("/save")
    @ApiOperation("保存项目数据")
    public ResultDTO save(@Valid @RequestBody TgProjectVO tgProjectVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        TgProject tgProject = TgProjectStructMapper.INSTANCE.toTgProject(tgProjectVO);
        return projectSaver.insertOrUpdateTgProject(tgProject,baseUser);
    }
    @PostMapping("/active")
    @ApiOperation("删除项目数据")
    public ResultDTO active(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return projectSaver.activeTgProject(guidContainerVO.getGuid());
    }
    @PostMapping("/del")
    @ApiOperation("删除项目数据")
    public ResultDTO save(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return projectSaver.deleteTgProject(guidContainerVO.getGuid());
    }
}
