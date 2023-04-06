package com.qiyuan.base2048.controller.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.vuecomponent.TableFilterDTO;
import com.qiyuan.base2048.pojo.vo.process.TableNameContainer;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.MybatisPlusGenerator;
import com.qiyuan.base2048.service.generator.ProjectGetter;
import com.qiyuan.base2048.service.generator.TableAndViewGetter;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/generator/mybatisplus")
@Api("Mybatis Plus代码生成操作接口")
public class MybatisPlusGeneratorController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private TableAndViewGetter tableAndViewGetter;
    @Resource
    private MybatisPlusGenerator mybatisPlusGenerator;
    @Resource
    private ProjectGetter projectGetter;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;

    @PostMapping("/table/page")
    @ApiOperation("分页获取Oracle数据表列表")
    public ResultDTO getTablePage(@Valid @RequestBody TableFilterDTO tableFilterDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return tableAndViewGetter.getPage(tableFilterDTO);
    }
    @PostMapping("/entity/source")
    @ApiOperation("生成数据表实体类源代码")
    public ResultDTO generateEntitySource(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateEntitySource(tableNameContainer.getTableName(),tgProject.getPackageEntity(),generatorInfoAdapter.getMpEntityFilePath(tgProject),tgProject.getAuthor());
    }
    @PostMapping("/entity")
    @ApiOperation("生成数据表实体类")
    public ResultDTO generateEntity(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateEntity(tableNameContainer.getTableName(),tgProject.getPackageEntity(),generatorInfoAdapter.getMpEntityFilePath(tgProject),tgProject.getAuthor(),tableNameContainer.isOverwrite());
    }
    @PostMapping("/vo/source")
    @ApiOperation("生成数据表实体类VO源代码")
    public ResultDTO generateVoSource(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateVoSource(tableNameContainer.getTableName(),tgProject.getPackageVo(),generatorInfoAdapter.getVoFilePath(tgProject),tgProject.getAuthor());
    }
    @PostMapping("/vo")
    @ApiOperation("生成数据表实体类VO")
    public ResultDTO generateVo(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateVo(tableNameContainer.getTableName(),tgProject.getPackageVo(),generatorInfoAdapter.getVoFilePath(tgProject),tgProject.getAuthor(),tableNameContainer.isOverwrite());
    }
    @PostMapping("/mapper/source")
    @ApiOperation("生成数据表Mapper类及XML源码")
    public ResultDTO generateMapperSource(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateMapperSource(tableNameContainer.getTableName(),tgProject.getPackageMapper(),tgProject.getPackageEntity(),generatorInfoAdapter.getMpMapperFilePath(tgProject),tgProject.getXmlOutputDir(),tgProject.getAuthor());
    }
    @PostMapping("/mapper")
    @ApiOperation("生成数据表Mapper类及XML")
    public ResultDTO generateMapper(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateMapper(tableNameContainer.getTableName(),tgProject.getPackageMapper(),tgProject.getPackageEntity(),generatorInfoAdapter.getMpMapperFilePath(tgProject),tgProject.getXmlOutputDir(),tgProject.getAuthor());
    }
    @PostMapping("/service/source")
    @ApiOperation("生成数据表Service接口和实现类源码")
    public ResultDTO generateServiceSource(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateServiceSource(tableNameContainer.getTableName(),tgProject.getPackageService(),tgProject.getPackageEntity(),tgProject.getPackageMapper(),generatorInfoAdapter.getMpServiceFilePath(tgProject),tgProject.getAuthor());
    }
    @PostMapping("/service")
    @ApiOperation("生成数据表Service接口和实现类")
    public ResultDTO generateService(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateService(tableNameContainer.getTableName(),tgProject.getPackageService(),tgProject.getPackageEntity(),tgProject.getPackageMapper(),generatorInfoAdapter.getMpServiceFilePath(tgProject),tgProject.getAuthor());
    }
    @PostMapping("/controller/source")
    @ApiOperation("生成数据表Controller源码")
    public ResultDTO generateControllerSource(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateControllerSource(tableNameContainer.getTableName(),tgProject.getPackageController(),generatorInfoAdapter.getMpControllerFilePath(tgProject),tgProject.getAuthor());
    }
    @PostMapping("/controller")
    @ApiOperation("生成数据表Controller")
    public ResultDTO generateController(@Valid @RequestBody TableNameContainer tableNameContainer,BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TgProject tgProject = projectGetter.getActiveProject();
        return mybatisPlusGenerator.generateController(tableNameContainer.getTableName(),tgProject.getPackageController(),generatorInfoAdapter.getMpControllerFilePath(tgProject),tgProject.getAuthor());
    }
}
