package com.qiyuan.gateway4096.feign.hystricImpl;

import com.qiyuan.bautil.dto.MenuDTO;
import com.qiyuan.gateway4096.feign.FeignBaseService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-26 15:57
 **/
@Service
@Slf4j
@Data
public class FeignBaseServiceHystricImpl implements FeignBaseService {
    private Throwable cause;


    @Override
    public List<String> fetchRoleListByUri(String uri) throws Exception {
        log.error("feign 根据URI获取可访问的角色列表时失败:{}", cause);
        return new ArrayList<>();
    }

    @Override
    public MenuDTO fetchMenuDtoByUri(String uri) {
        log.error("feign 根据URI获取菜单功能资源MenuDTO时失败:{}", cause);
        return null;
    }
}
