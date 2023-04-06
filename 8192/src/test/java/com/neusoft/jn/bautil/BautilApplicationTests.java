package com.neusoft.jn.bautil;

import cn.hutool.http.useragent.UserAgent;
import com.qiyuan.bautil.dto.LoginForm;
import com.qiyuan.bautil.util.PojoUtil;
import com.qiyuan.bautil.util.StringTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BautilApplicationTests {

    @Test
    void contextLoads() {

    }
    @Test
    void test1(){
        UserAgent userAgent = new UserAgent();
        System.out.println(userAgent.getBrowser());
    }
}
