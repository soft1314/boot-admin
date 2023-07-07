package com.qiyuan.base2048.controller;

import com.qiyuan.base2048.mapper.mybatis.TrRoleResourceMapper;
import com.qiyuan.base2048.service.permission.ResourceDataGetter;
import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-26 11:21
 **/
@Slf4j
@RestController
@RequestMapping("/api/system/free/resource")
@Api("用于网关使用的的自由访问资源")
public class FreeResourceController {
    @Resource
    private ResourceDataGetter resourceDataGetter;
    @Resource
    private TrRoleResourceMapper trRoleResourceMapper;;
    @GetMapping("/menudto")
    MenuDTO selectMenuDtoByUri(@RequestParam("uri") String uri) throws Exception{
        MenuDTO menuDTO = resourceDataGetter.getMenuDtoByUri(uri);
        return menuDTO;
    }

    @GetMapping("/role/list")
    List<String> selectRoleListByUri(@RequestParam("uri") String uri) throws Exception{
        List<String> roleList = trRoleResourceMapper.selectRoleListByResourceUri(uri, IsEnabledEnum.ENABLED.getStringValue(), IsDeletedEnum.NOTDELETED.getStringValue());
        return roleList;
    }
}
