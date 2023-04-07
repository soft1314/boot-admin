# boot-admin

#### 介绍
boot-admin 是一款采用前后端分离架构模式的后台管理系统解决方案。既有助于学习微服务开发，也可直接作为实际项目的初始框架。不过渡封装，简洁高效，展现技术本质!

#### 软件技术路线

**前端**：基于 vue-element-admin 改造实现（官网：https://panjiachen.gitee.io/vue-element-admin-site/zh/ ）；</br>
**后端**：在SpringBoot基础上搭建的一个Java基础开发平台，MyBatis-Plus为数据访问层，使用Redis对常用数据进行缓存，集成整合Nacos、Seata、SpringCloudGateway、SpringBootAdmin、Flowable、Quartz、Liquibase、Mapstruct、OpenFeign、i18N、JWT、Freemarker等。


#### 主要功能
![image](https://gitee.com/soft1314/boot-admin-vue/raw/master/func.png)

#### 模块介绍
- 1024：前端
- 2048：后端基础模块（系统管理、工作流、定时任务）
- 3072：后端工作流模块
- 4195：网关 Spring Cloud Gateway
- 5120: 监控管理 Spring Boot Admin
- 8192: 后端基础工具（JAR包）