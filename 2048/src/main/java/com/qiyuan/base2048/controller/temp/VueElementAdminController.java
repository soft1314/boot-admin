package com.qiyuan.base2048.controller.temp;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-02 9:34
 **/
@Slf4j
@RestController
@RequestMapping("/api/system/free/vue-element-admin")
@ResponseBodyInController
public class VueElementAdminController {
    @GetMapping("/transaction/list")
    public ResultDTO transactionList(){
        Map map = new HashMap<>();
        map.put("total",20);

        Map map1 = new HashMap<>();
        map1.put("order_no", IdWorker.getIdStr());
        map1.put("timestamp", new Date());
        map1.put("username", "username");
        map1.put("price", 1500);
        map1.put("status",1);
        List list = new ArrayList<>();
        list.add(map1);
        map1 = new HashMap<>();
        map1.put("order_no", "测试数据");
        map1.put("timestamp", new Date());
        map1.put("username", "username");
        map1.put("price", 15000);
        map1.put("status",1);
        list.add(map1);
        map.put("items",list);


        return ResultDTO.success(map);
    }
}
