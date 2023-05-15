package com.qiyuan.base2048.controller.oa;

import com.qiyuan.base2048.pojo.vo.generated.ToNotifyScene1DataVO;
import com.qiyuan.base2048.pojo.vo.generated.ToNotifyScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.oa.NotifyAdmDivRelationshipVO;
import com.qiyuan.base2048.service.oa.NotifyDivRelationSaver;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.service.oa.IToNotifyScene1Service;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 通知通告主表 剧情1Controller
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-5-8 10:38:33
 */
@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/to-notify/scene1")
@Api("to_notify表剧情1接口")
public class ToNotifyController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private IToNotifyScene1Service scene1Service;

    @PostMapping("/table/page")
    @ApiOperation("分页获取项目数据表列表")
    public ResultDTO getTablePage(@Valid @RequestBody ToNotifyScene1QueryVO queryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return scene1Service.getPage(queryVO, false);
    }

    @PostMapping("/save")
    @ApiOperation("保存数据")
    public ResultDTO save(@Valid @RequestBody ToNotifyScene1DataVO dataVO, BindingResult bindingResult) throws Exception {
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
