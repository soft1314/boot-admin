package com.qiyuan.base2048.controller.sysmanage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.mapper.mapstruct.TbResourceStructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.pojo.vo.eltable.MenuVO;
import com.qiyuan.base2048.pojo.vo.query.ResourcePageQueryByRoleIdParamVO;
import com.qiyuan.base2048.pojo.vo.query.ResourcePageQueryParamVO;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.base2048.pojo.vo.save.ResourceRoleVO;
import com.qiyuan.base2048.pojo.vo.save.ResourceVO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.permission.ResourceDataGetter;
import com.qiyuan.base2048.service.permission.ResourceDataSaver;
import com.qiyuan.base2048.service.permission.RoleDataGetter;
import com.qiyuan.base2048.service.permission.RoleDataSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.TransferDataDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-01 22:32
 **/
@RestController
@RequestMapping("/api/system/auth/manage/resource")
@Slf4j
@ResponseBodyInController
@Api("系统资源管理相关的api")
public class ResourceManageController {
    @Autowired
    private FormValidator formValidator;
    @Autowired
    private ResourceDataGetter resourceDataGetter;
    @Autowired
    private ResourceDataSaver resourceDataSaver;
    @Autowired
    private RoleDataGetter roleDataGetter;
    @Autowired
    private RoleDataSaver roleDataSaver;

    @PostMapping("/page")
    @ApiOperation("分页查询系统资源")
    public ResultDTO getResourcePage(@Valid @RequestBody ResourcePageQueryParamVO resourcePageQueryParamVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        IPage<TbResource> tbResourceIPage = resourceDataGetter.get(resourcePageQueryParamVO.getRoleId(),resourcePageQueryParamVO.getResourceName(),resourcePageQueryParamVO.getUri(),resourcePageQueryParamVO.getResourceType(),resourcePageQueryParamVO.getEnabled(),resourcePageQueryParamVO.getCurrentPage(),resourcePageQueryParamVO.getPageSize());
        return ResultDTO.success(tbResourceIPage);
    }
    @PostMapping("/update")
    @ApiOperation("保存系统资源")
    public ResultDTO updateResource(@Valid @RequestBody ResourceVO resourceVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TbResource tbResource = TbResourceStructMapper.INSTANCE.map2TbResource(resourceVO);
        boolean updateRet = resourceDataSaver.updateResource(tbResource);
        if(updateRet) {
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("record.update.error"));
        }
    }
    @PostMapping("/add")
    @ApiOperation("插入系统资源")
    public ResultDTO addResource(@Valid @RequestBody ResourceVO resourceVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TbResource tbResource = TbResourceStructMapper.INSTANCE.map2TbResource(resourceVO);
        boolean insertRet = resourceDataSaver.insertResource(tbResource);
        if(insertRet) {
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("record.insert.error"));
        }
    }
    @PostMapping("/page/byroleid")
    @ApiOperation("按角色ID分页查询功能点")
    public ResultDTO getSysFuctionPageByRoleId(@Valid @RequestBody ResourcePageQueryByRoleIdParamVO resourcePageQueryByRoleIdParamVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        IPage<TbResource> tbResourceIPage = resourceDataGetter.getByRoleId(resourcePageQueryByRoleIdParamVO.getRoleId(),resourcePageQueryByRoleIdParamVO.getCurrentPage(),resourcePageQueryByRoleIdParamVO.getPageSize());
        return ResultDTO.success(tbResourceIPage);
    }
    @ApiOperation("按资源GUID查询角色穿梭框数据")
    @GetMapping("/role/transfer")
    ResultDTO loadRoleTransferData(@RequestParam("resourceGuid") String resourceGuid) throws Exception{
        TransferDataDTO transferDataDTO = roleDataGetter.getResourceRoleTransferData(resourceGuid);
        return ResultDTO.success(transferDataDTO);
    }
    @PostMapping("/role/save")
    @ApiOperation("保存系统资源的可用角色权限数据")
    ResultDTO saveResourcePower(@Valid @RequestBody ResourceRoleVO resourceRoleVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        roleDataSaver.saveResourcePower(resourceRoleVO.getResourceGuid(),resourceRoleVO.getRoleGuids());
        return ResultDTO.successCustom(MessageUtils.get("power.save.success"),null);
    }
    @PostMapping("/menu/top/page")
    @ApiOperation("分页查询系统资源")
    public ResultDTO fetchTopMenuPage(@Valid @RequestBody ResourcePageQueryParamVO resourcePageQueryParamVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        IPage<MenuVO> tbResourceIPage = resourceDataGetter.getTopMenu(resourcePageQueryParamVO.getRoleId(),resourcePageQueryParamVO.getResourceName(),resourcePageQueryParamVO.getUri(),resourcePageQueryParamVO.getEnabled(),resourcePageQueryParamVO.getCurrentPage(),resourcePageQueryParamVO.getPageSize());
        return ResultDTO.success(tbResourceIPage);
    }
    @GetMapping("/menu/children/all")
    @ApiOperation("分页查询系统资源")
    public ResultDTO fetchMenuAllChildren(@RequestParam("guid") String guid) throws Exception{
        List<MenuVO> menuVOList = resourceDataGetter.getAllChilden(guid);
        return ResultDTO.success(menuVOList);
    }
    @ApiOperation("保存(添加或修改)菜单")
    @PostMapping("/menu/save")
    ResultDTO saveMenu(@Valid @RequestBody ResourceVO resourceVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TbResource tbResource = TbResourceStructMapper.INSTANCE.map2TbResource(resourceVO);
        if(resourceDataSaver.insertOrUpdateResource(tbResource)) {
            return ResultDTO.successCustom(MessageUtils.get("menu.save.success"), null);
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("menu.save.error"));
        }
    }
    @ApiOperation("通过GUID逻辑删除资源")
    @PostMapping("/del")
    ResultDTO delResoure(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ResultDTO resultDTO = resourceDataSaver.deleteByGuid(guidContainerVO.getGuid());
        return resultDTO;
    }
}
