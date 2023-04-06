package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.query.TbOrganizationScene2QueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.pojo.vo.save.TbOrganizationScene2DataVO;
import com.qiyuan.base2048.service.manage.ITbOrganizationScene2Service;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 组织机构基础表 剧情1Controller
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-29 12:20:14
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/tb-organization/scene2")
@Api("tb_organization表剧情2接口")
public class TbOrganizationScene2Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private ITbOrganizationScene2Service myService;

  @PostMapping("/top/page")
  @ApiOperation("分页查询顶层数据")
  public ResultDTO fetchTopPage(@Valid @RequestBody TbOrganizationScene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    ResultDTO resultDTO = myService.getTop(queryVO,false);
    return resultDTO;
  }
  @PostMapping("/page")
  @ApiOperation("分页查询数据")
  public ResultDTO fetchPage(@Valid @RequestBody TbOrganizationScene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
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
  ResultDTO save(@Valid @RequestBody TbOrganizationScene2DataVO dataVO, BindingResult bindingResult) throws Exception{
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
  @GetMapping("/children/node")
  @ApiOperation("获取节点列表")
  public ResultDTO fetchTopNode(@RequestParam("code") String code,@RequestParam("admDivCode") String admDivCode) throws Exception{
    ResultDTO resultDTO = myService.getOneLevelNode(code,admDivCode);
    return resultDTO;
  }
}
