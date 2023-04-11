package com.qiyuan.base2048.mapper.mybatis.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.util.UserTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-07 17:12
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        Date now = new Date();
        String operator = "[系统]";
        BaseUser baseUser = UserTool.getBaseUser();
        if(baseUser != null){
            operator = baseUser.getUserName()+"["+baseUser.getLogonName()+"]";
        }
        this.setFieldValByName("createTime",now,metaObject);
        this.setFieldValByName("createBy",operator,metaObject);
        this.setFieldValByName("modifyTime",now,metaObject);
        this.setFieldValByName("modifyBy",operator,metaObject);

        this.setFieldValByName("guid", IdWorker.getIdStr(),metaObject);
        this.setFieldValByName("version", 0,metaObject);
        /**  以下官方文档，为什么有问题
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        // 或者
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
        // 或者
        this.fillStrategy(metaObject, "createTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
         **/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        Date now = new Date();
        BaseUser baseUser = UserTool.getBaseUser();
        String operator = "[系统]";
        if(baseUser != null){
            operator = baseUser.getUserName()+"["+baseUser.getLogonName()+"]";
        }

        this.setFieldValByName("modifyTime",now,metaObject);
        this.setFieldValByName("modifyBy",operator,metaObject);
//        this.setFieldValByName("datestamp",now,metaObject);
        /** 以下官方文档，为什么有问题
        this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
         或者
        this.strictUpdateFill(metaObject, "modifyTime", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
        // 或者
        this.fillStrategy(metaObject, "modifyTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
         **/
    }
}
