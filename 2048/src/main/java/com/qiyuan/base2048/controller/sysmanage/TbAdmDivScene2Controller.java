package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.query.TbAdmDivScene2QueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.pojo.vo.save.TbAdmDivScene2DataVO;
import com.qiyuan.base2048.service.manage.ITbAdmDivScene2Service;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.CascaderDataVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 行政区划表 剧情1Controller
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-24 8:56:59
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/tb-adm-div/scene2")
@Api("tb_adm_div表剧情2接口")
public class TbAdmDivScene2Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private ITbAdmDivScene2Service myService;
//  @Autowired
//  private AdminDivConfig adminDivConfig;
  @Value("${base2048.default.admin-div.value}")
  private List<String> defaultAdminDivValue;
  @Value("${base2048.default.admin-div.label}")
  private String defaultAdminDivLabel;

  @PostMapping("/top/page")
  @ApiOperation("分页查询顶层数据")
  public ResultDTO fetchTopPage(@Valid @RequestBody TbAdmDivScene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    ResultDTO resultDTO = myService.getTop(queryVO,false);
    return resultDTO;
  }
  @PostMapping("/page")
  @ApiOperation("分页查询全部数据")
  public ResultDTO fetchPage(@Valid @RequestBody TbAdmDivScene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
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
  ResultDTO save(@Valid @RequestBody TbAdmDivScene2DataVO dataVO, BindingResult bindingResult) throws Exception{
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
  @ApiOperation("获取顶层数据节点列表")
  public ResultDTO fetchTopNode(@RequestParam("code") String code) throws Exception{
    ResultDTO resultDTO = myService.getOneLevelNode(code);
    return resultDTO;
  }
  @GetMapping("/default/value")
  @ApiOperation("获取应用的缺省区划，级联数据默认值")
  public ResultDTO fetchDefaultValue() throws Exception{
    CascaderDataVO cascaderDataVO = new CascaderDataVO();
    cascaderDataVO.setLeafLabel(defaultAdminDivLabel);
    cascaderDataVO.setValue(defaultAdminDivValue);
    return ResultDTO.success(cascaderDataVO);
  }
}
