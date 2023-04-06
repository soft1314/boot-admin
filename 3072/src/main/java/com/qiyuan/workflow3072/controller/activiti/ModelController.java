package com.qiyuan.workflow3072.controller.activiti;

import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import com.qiyuan.workflow3072.controller.BaseController;
import com.qiyuan.workflow3072.pojo.vo.ModelDataVO;
import com.qiyuan.workflow3072.pojo.vo.ModelQueryVO;
import com.qiyuan.workflow3072.service.MyModelService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/api/workflow/auth/activiti")
@RestController
@ResponseBodyInController
@Slf4j
public class ModelController extends BaseController {
    @Resource
    private FormValidator formValidator;
    @Autowired
    private MyModelService modelService;

    @PostMapping("/model/page")
    @ApiOperation("分页获取项目数据表列表")
    public ResultDTO getTablePage(@Valid @RequestBody ModelQueryVO queryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return modelService.getPage(queryVO);
    }
    @PostMapping("/model/add")
    @ApiOperation("保存数据")
    public ResultDTO save(@Valid @RequestBody ModelDataVO dataVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        return modelService.addNewModel(dataVO, baseUser);
    }

    @PostMapping("/model/del")
    @ApiOperation("删除数据")
    public ResultDTO del(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return modelService.delete(guidContainerVO.getGuid());
    }

    @PostMapping("/model/deploy")
    public ResultDTO deploy(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return modelService.deploy(guidContainerVO.getGuid());
    }
    @PostMapping("/model/xml")
    public ResultDTO genXml(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return modelService.genXml(guidContainerVO.getGuid());
    }
}
