package com.qiyuan.base2048.controller.generated;

import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3StartProcessDataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3CompleteTaskDataVO;
import com.qiyuan.base2048.service.generated.IDemoLeaveScene3Service;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.GuidContainerVO;
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


/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本工作流）请假 剧情3Controller
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:27:24
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/demo-leave/scene3")
@Api("demo_leave表剧情3接口")
public class DemoLeaveScene3Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private IDemoLeaveScene3Service sceneService;

  @PostMapping("/start")
  @ApiOperation("插入数据并启动流程")
  public ResultDTO insertAndStart(@Valid @RequestBody DemoLeaveScene3StartProcessDataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return sceneService.insertBizDataAndStartProcess(dataVO, baseUser);
  }
  @PostMapping("/task/complete")
  @ApiOperation("修改数据并完成任务")
  public ResultDTO updateAndComplete(@Valid @RequestBody DemoLeaveScene3CompleteTaskDataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return sceneService.updateBizDataAndCompleteTask(dataVO, baseUser);
  }
  @PostMapping("/info")
  @ApiOperation("通过businessId获取业务数据")
  public ResultDTO getOne(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    return sceneService.getOne(guidContainerVO.getGuid());
  }
}
