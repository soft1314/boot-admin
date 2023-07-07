package com.qiyuan.base2048.mpgenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-02 13:36
 **/
public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:oracle:thin:@127.0.0.1:1521:orcl","base2048","base2048")
                .globalConfig(builder -> {
                    builder.author("虚领顶劲气沉丹田")
                            .enableSwagger()
//                            .fileOverride()
                            .outputDir("C:\\life\\hobby\\boot-admin\\2048\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.qiyuan")
                            .moduleName("base2048")
//                            .controller("")
                            .entity("mapper.mybatis.entity")
                            .service("service.mybatis")
                            .serviceImpl("service.mybatis.impl")
                            .mapper("mapper.mybatis")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,"C:\\life\\hobby\\boot-admin\\2048\\src\\main\\java\\com\\qiyuan\\base2048\\mapper\\mybatis\\xml"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("TB_RESOURCE")
                            .entityBuilder()
                    .enableTableFieldAnnotation();
                })
                .templateConfig(builder -> {
                    builder.entity("/templates/entity.java");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}


