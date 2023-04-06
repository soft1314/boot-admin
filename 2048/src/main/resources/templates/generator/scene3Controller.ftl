package ${marker.packageUrl};

import ${marker.voPackageUrl}.${marker.entityName}Scene3StartProcessDataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene3CompleteTaskDataVO;
import ${marker.servicePackageUrl}.I${marker.entityName}Scene3Service;
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
    * ${marker.tableComment} 剧情3Controller
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene3")
@Api("${marker.tableNameLowercase}表剧情3接口")
public class ${marker.entityName}Scene3Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private I${marker.entityName}Scene3Service sceneService;

  @PostMapping("/start")
  @ApiOperation("插入数据并启动流程")
  public ResultDTO insertAndStart(@Valid @RequestBody ${marker.entityName}Scene3StartProcessDataVO dataVO, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    BaseUser baseUser = UserTool.getBaseUser();
    return sceneService.insertBizDataAndStartProcess(dataVO, baseUser);
  }
  @PostMapping("/task/complete")
  @ApiOperation("修改数据并完成任务")
  public ResultDTO updateAndComplete(@Valid @RequestBody ${marker.entityName}Scene3CompleteTaskDataVO dataVO, BindingResult bindingResult) throws Exception {
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
