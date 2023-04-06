package com.qiyuan.workflow3072;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Workflow3072ApplicationTests {
    @Autowired
    private RepositoryService repositoryService;

    @Test
    void contextLoads() {
    }
    @Test
    void deployFlow() {
//        try {
//            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
//                    .addClasspathResource("C:\\life\\hobby\\boot-admin\\3072\\src\\main\\resources\\processes\\ExpenseProcess.bpmn20.xml");
//            Deployment deployment = deploymentBuilder.deploy();
//            System.out.println("成功：部署工作流成：");
//        } catch (Exception e) {
//            System.out.println("失败：部署工作流：" + e);
//        }
    }

}
