# boot-admin

#### 介绍

**boot-admin** 是一款采用前后端分离架构模式的后台管理框架。系统提炼自实际项目，兼具**RuoYi-Vue前端分离版**和**Ruoyi-Cloud微服务版**功能与技术特点。  

**boot-admin** 既有助于学习微服务开发，也可直接作为实际项目的初始框架。不过渡封装，简洁高效，展现技术本质!

#### 软件技术路线

>**前端**：
> * 基于 [vue-element-admin](https://panjiachen.gitee.io/vue-element-admin-site/zh/) 开源前端框架
> * 基于渐进式Javascript框架[Vue.js 2.x](https://v2.cn.vuejs.org/)
> * 基于 [Element UI 2.x](https://element.eleme.cn/#/zh-CN)

>**后端**：
>* 基于JDK1.8+语言包
>* 基于SpringBoot2.5.x、SpringCloud2020.x、SpringCloudAlibaba2021.x核心框架
>* 基于MyBatis-Plus3.4.x持久层框架
>* 整合Alibaba Nacos2.2作为服务注册、服务发现和配置中心
>* 整合**Alibaba Seata**1.4.2，以AT模式作为分布式事务解决方案
>* 整合Spring Cloud **Gateway**作为微服务网关
>* 在网关中统一对系统资源进行全面权限控制管理
>* 基本spring-boot-starter-cache和Redis作为缓存框架
>* 整合**Flowable**6.x作为工作流引擎
>* 整合**Drools**7.x作为业务规则引擎
>* 整合Quartz2.3.x作为作业调度框架
>* 整合Liquibase4.3.x进行数据库版本跟踪管理
>* 支持标准JWT令牌授权模式
>* 支持基于Redis的双令牌机制和在线用户管理
>* 整合Spring Boot Admin2.x作为微服务监控和管理
>* 整合OpenFeign作为声明式HTTP请求客户端
>* 前后端均集合国际化 i18n 方案
>* 整合FreeMarker作为代码生成模板引擎
>* 整合Mapstruct作为生成类型安全的bean映射类
>* 日志管理：SLF4J
>* 工具类：Apache Commons、hutool、Jackson


#### 微服务架构图

![image](https://gitee.com/soft1314/boot-admin-vue/raw/master/arch.png)

#### 主要功能框图

![image](https://github.com/soft1314/boot-admin/blob/master/func.png?raw=true)

#### 模块介绍

>*   1024：前端
>*   2048：后端基础模块（系统管理、工作流、定时任务）
>*   3072：后端工作流模块
>*   4096：网关 Spring Cloud Gateway
>*   5120：监控管理 Spring Boot Admin
>*   6144：日志管理
>*   8192：后端基础工具（JAR包）
