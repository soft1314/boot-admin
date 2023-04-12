package com.qiyuan.base2048.controller.generated;

import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2DataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2QueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.service.generated.IDemoOrgScene2Service;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（层次CRUD）单位表 剧情1Controller
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:26:50
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/demo-org/scene2")
@Api("demo_org表剧情2接口")
public class DemoOrgScene2Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private IDemoOrgScene2Service myService;

  @PostMapping("/top/page")
  @ApiOperation("分页查询顶层数据")
  public ResultDTO fetchTopPage(@Valid @RequestBody DemoOrgScene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    ResultDTO resultDTO = myService.getTop(queryVO,false);
    return resultDTO;
  }
  @PostMapping("/page")
  @ApiOperation("分页查询数据")
  public ResultDTO fetchPage(@Valid @RequestBody DemoOrgScene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    ResultDTO resultDTO = myService.getPage(queryVO,false);
    return resultDTO;
  }
  @GetMapping("/children/all")
  @ApiOperation("查询下一层数据")
  public ResultDTO fetchAllChildren(@RequestParam("guid") String guid) throws Exception{
    ResultDTO resultDTO = myService.getAllChilden(guid);
    return resultDTO;
  }
  @ApiOperation("保存")
  @PostMapping("/save")
  ResultDTO save(@Valid @RequestBody DemoOrgScene2DataVO dataVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    ResultDTO resultDTO = myService.insertOrUpdate(dataVO,baseUser);
    return resultDTO;
  }
  @ApiOperation("通过GUID逻辑删除")
  @PostMapping("/del")
  ResultDTO delResoure(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    ResultDTO resultDTO = myService.delete(guidContainerVO.getGuid());
    return resultDTO;
  }
}
