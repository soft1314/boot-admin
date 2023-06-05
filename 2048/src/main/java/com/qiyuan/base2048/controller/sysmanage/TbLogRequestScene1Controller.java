package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.save.TbLogRequestScene1DataVO;
import com.qiyuan.base2048.pojo.vo.query.TbLogRequestScene1QueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.service.manage.ITbLogRequestScene1Service;
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
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 访问日志表 剧情1Controller
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/tb-log-request/scene1")
@Api("tb_log_request表剧情1接口")
public class TbLogRequestScene1Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private ITbLogRequestScene1Service scene1Service;

  @PostMapping("/table/page")
  @ApiOperation("分页获取项目数据表列表")
  public ResultDTO getTablePage(@Valid @RequestBody TbLogRequestScene1QueryVO queryVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    return scene1Service.getPage(queryVO, true);
  }

  @PostMapping("/save")
  @ApiOperation("保存数据")
  public ResultDTO save(@Valid @RequestBody TbLogRequestScene1DataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return scene1Service.insertOrUpdate(dataVO, baseUser);
}

  @PostMapping("/del")
  @ApiOperation("删除数据")
  public ResultDTO del(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    return scene1Service.delete(guidContainerVO.getGuid());
  }
}
