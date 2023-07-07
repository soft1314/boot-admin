package ${marker.packageUrl};

import ${marker.voPackageUrl}.${marker.entityName}Scene4CompleteTaskDataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene4DataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene4QueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import ${marker.servicePackageUrl}.I${marker.entityName}Scene4Service;
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
    * ${marker.tableComment} 剧情4Controller
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene4")
@Api("${marker.tableNameLowercase}表剧情4接口")
public class ${marker.entityName}Scene4Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private I${marker.entityName}Scene4Service scene4Service;

  @PostMapping("/table/page")
  @ApiOperation("分页获取项目数据表列表")
  public ResultDTO getTablePage(@Valid @RequestBody ${marker.entityName}Scene4QueryVO queryVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    return scene4Service.getPage(queryVO, true);
  }

  @PostMapping("/save")
  @ApiOperation("保存数据")
  public ResultDTO save(@Valid @RequestBody ${marker.entityName}Scene4DataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return scene4Service.insertOrUpdate(dataVO, baseUser);
  }
  @PostMapping("/save-start")
  @ApiOperation("保存数据并启动流程")
  public ResultDTO saveAndStartProcess(@Valid @RequestBody ${marker.entityName}Scene4DataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return scene4Service.saveAndStartProcess(dataVO, baseUser);
  }

  @PostMapping("/del")
  @ApiOperation("删除数据")
  public ResultDTO del(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    return scene4Service.delete(guidContainerVO.getGuid());
  }

  @PostMapping("/task/complete")
  @ApiOperation("修改数据并完成任务")
  public ResultDTO updateAndComplete(@Valid @RequestBody ${marker.entityName}Scene4CompleteTaskDataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return scene4Service.updateBizDataAndCompleteTask(dataVO, baseUser);
  }

  @PostMapping("/info")
  @ApiOperation("通过businessId获取业务数据")
  public ResultDTO getOne(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    return scene4Service.getOne(guidContainerVO.getGuid());
  }
}
