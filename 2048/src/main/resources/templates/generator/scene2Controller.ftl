package ${marker.packageUrl};

import ${marker.voPackageUrl}.${marker.entityName}Scene2DataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene2QueryVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import ${marker.servicePackageUrl}.I${marker.entityName}Scene2Service;
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
    * ${marker.tableComment} 剧情1Controller
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/${marker.tableNameLowercaseMinusSign}/scene2")
@Api("${marker.tableNameLowercase}表剧情2接口")
public class ${marker.entityName}Scene2Controller {
  @Resource
  private FormValidator formValidator;
  @Resource
  private I${marker.entityName}Scene2Service myService;

  @PostMapping("/top/page")
  @ApiOperation("分页查询顶层数据")
  public ResultDTO fetchTopPage(@Valid @RequestBody ${marker.entityName}Scene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
    if (bindingResult.hasErrors()) {
      return formValidator.generateMessage(bindingResult);
    }
    ResultDTO resultDTO = myService.getTop(queryVO,false);
    return resultDTO;
  }
  @PostMapping("/page")
  @ApiOperation("分页查询数据")
  public ResultDTO fetchPage(@Valid @RequestBody ${marker.entityName}Scene2QueryVO queryVO, BindingResult bindingResult) throws Exception{
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
  ResultDTO save(@Valid @RequestBody ${marker.entityName}Scene2DataVO dataVO, BindingResult bindingResult) throws Exception{
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
