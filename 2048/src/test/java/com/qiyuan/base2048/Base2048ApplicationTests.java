package com.qiyuan.base2048;

import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qiyuan.base2048.cache.ResourceCacheService;
import com.qiyuan.bautil.service.component.RedisOnlineUserUtils;
import com.qiyuan.bautil.util.StringTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

//import com.qiyuan.base2048.service.workflow.ProcessDefinitionGetter;
//import com.qiyuan.base2048.service.workflow.ProcessDefinitionHandler;
//import com.qiyuan.base2048.service.workflow.ProcessInstanceGetter;
//import com.qiyuan.base2048.service.workflow.TaskGetter;
//import org.flowable.engine.HistoryService;
//import org.flowable.engine.RuntimeService;
//import org.flowable.engine.TaskService;
//import org.flowable.engine.history.HistoricActivityInstance;
//import org.flowable.engine.history.HistoricProcessInstance;
//import org.flowable.engine.runtime.Execution;
//import org.flowable.engine.runtime.ProcessInstance;
//import org.flowable.task.api.Task;
//import org.flowable.task.api.history.HistoricTaskInstance;

@SpringBootTest
class Base2048ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generateNewId(){
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
        System.out.println(IdWorker.getIdStr());
    }
    @Resource
    private ResourceCacheService resourceCacheService;
//    @Test
//    void testCache() throws Exception{
//        TenantContextHolder.setTenantId("DEMO");
//        MenuDTO menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        resourceCacheService.reload();
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//        menuDTO = resourceCacheService.getMenuDtoByUri("/api/system/free/user/login");
//        System.out.println(menuDTO.getSrvName());
//
//
//    }
//    @Test
//    void testSm4(){
//        String originTxt = "这是一次注定载入心的党中央周围，必将在新时代新征程上赢得更加伟大的胜利和荣光。";
//        System.out.println("原文: " + originTxt);
//        String cipherTxt = Sm4EncryptUtil.encrypt(originTxt,"abc1111111111333","iviviviviviviviv");
//        System.out.println("密文: " + cipherTxt);
//        String plainTxt = Sm4EncryptUtil.decrypt(cipherTxt,"abc1111111111333","iviviviviviviviv");
//        System.out.println("解密结果: " + plainTxt);
//
//        cipherTxt = SmUtil.sm4().encryptBase64(originTxt);
//        System.out.println("密文: " + cipherTxt);
//        plainTxt = SmUtil.sm4().decryptStr(Base64.decode(cipherTxt));
//        System.out.println("解密结果: " + plainTxt);
//    }
//    @Resource
//    private ProcessDefinitionGetter processDefinitionGetter;
//    @Test
//    void testFlowable1() throws Exception{
//        List<ProcessDefinitionDTO>  processDefinitionDTOS = processDefinitionGetter.getAllProcessDefinitionList("t1");
//        processDefinitionDTOS.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//    }

//    @Resource
//    private ProcessInstanceGetter processInstanceGetter;
//    @Test
//    void testFlowable2() throws Exception{
//        List<HistoricProcessInstance>  historicProcessInstances = processInstanceGetter.getAllHistoricProcessInstanceList("t1");
//        historicProcessInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//    }
//    @Test
//    void testFlowable3() throws Exception{
//        List<HistoricActivityInstance>  historicActivityInstances = processInstanceGetter.getAllHistoricActivityInstanceList("t1");
//        historicActivityInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//    }
//    @Test
//    void testFlowable4() throws Exception{
//        List<ProcessInstance>  processInstances = processInstanceGetter.getALlProcessInstanceList("t1");
//        processInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//    }
//    @Autowired
//    private RuntimeService runtimeService;
//    @Test
//    void testFlowable5() throws Exception{
//        List<ProcessInstance>  processInstances = processInstanceGetter.getALlProcessInstanceList("t1");
//        processInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//            runtimeService.deleteProcessInstance(item.getId(),"test");
//        });
//    }
//    @Autowired
//    private ProcessDefinitionHandler processDefinitionHandler;
//    @Test
//    void testFlowable6() throws Exception{
//        String tenantId="t1";
//        processDefinitionHandler.test();

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("taskUser", "虚领顶劲气沉丹田");
//        map.put("money", 500);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("报销流程定义ID", map,tenantId);
//        System.out.println(processInstance.getId());

//        List<ProcessDefinitionDTO>  processDefinitionDTOS = processDefinitionGetter.getAllProcessDefinitionList("t1");
//        processDefinitionDTOS.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//        /** 所有流程实例，包括运行的和结束的 **/
//        List<HistoricProcessInstance>  historicProcessInstances = processInstanceGetter.getAllHistoricProcessInstanceList("t1");
//        historicProcessInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//        /** 包括开妈，线条等，没啥用处吧 **/
//        List<HistoricActivityInstance>  historicActivityInstances = processInstanceGetter.getAllHistoricActivityInstanceList("t1");
//        historicActivityInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });
//        /** 正在运行的流程实例 **/
//        List<ProcessInstance>  processInstances = processInstanceGetter.getALlProcessInstanceList("t1");
//        processInstances.stream().forEach(item -> {
//            System.out.println(item.toString());
//        });

//    }
//    @Autowired
//    private TaskGetter taskGetter;
//    @Test
//    void testFlowable7() throws Exception {
//        String tenantId = "t1";
//        List<Task> tasks = taskGetter.getTaskListByUserId("虚领顶劲气沉丹田",tenantId);
//        tasks.stream().forEach(item->{
//            System.out.println(item.getAssignee());
//        });
//    }
//    @Autowired
//    private TaskService taskService;
//    @Test
//    void testFlowable8() throws Exception {
//        taskService.setAssignee("b134e7c2-58fc-11ec-a7a9-4c77cb6b51e3","一个人");
//        taskService.complete("b134e7c2-58fc-11ec-a7a9-4c77cb6b51e3");
//        System.out.println("OK");
//    }
//    @Test
//    void testFlowable9() throws Exception {
//        processDefinitionHandler.test1();
//    }
//    @Test
//    void testFlowable10() throws Exception {
//        String tenantId = "t1";
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId("leave", tenantId);
//    }
//
//    @Test
//    void testFlowable11() throws Exception {
//        /** 所有当前的任务 **/
//        List<Task> tasks = taskService.createTaskQuery().taskTenantId("t1").list();
//        tasks.stream().forEach(task -> {
//            System.out.println(task.getName());
//        });
//
//        /**指定流程活动的任务 **/
//        List<Task> taskList = taskService.createTaskQuery().taskTenantId("t1").processInstanceId("b8116a12-58fa-11ec-87b9-4c77cb6b51e3").list();
//        taskList.stream().forEach(task -> {
//            System.out.println(task.getName());
//        });
//
//
//    }
//    @Autowired
//    private HistoryService historyService;
//    @Test
//    void testFlowable12() throws Exception {
//
//        Task task = taskService.createTaskQuery().taskTenantId("t1").processInstanceId("b8116a12-58fa-11ec-87b9-4c77cb6b51e3").singleResult();
//        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
//        String InstanceId = task.getProcessInstanceId();
//        List<Execution> executions = runtimeService
//                .createExecutionQuery()
//                .processInstanceId(InstanceId)
//                .list();
//
//        executions.stream().forEach(item->{
//            System.out.println(item.getName());
//        });
//
//    }


//    @Test
//    void testFlowable13() throws Exception {
//        /** 流程日志 ,即各个任务 **/
//       List<HistoricTaskInstance>  historicTaskInstances = historyService.createHistoricTaskInstanceQuery().taskTenantId("t1").processInstanceId("b8116a12-58fa-11ec-87b9-4c77cb6b51e3").list();
//       historicTaskInstances.stream().forEach(task->{
//           System.out.println(task.getEndTime());
//       });
//
//
//
//    }
//    @Test
//    void test1(){
//        LoginForm loginForm = new LoginForm();
//
//        PojoUtil.getDtoInfo(loginForm,LoginForm.class);
//    }
//    @Test
//    void testSm3(){
//        System.out.println(cn.hutool.crypto.SmUtil.sm3("aaaaaaaaaaaaaaaa"));
//        System.out.println(cn.hutool.crypto.SmUtil.sm3("aaaaaaaaaaaaffffffffffffffffffaaaa"));
//        System.out.println(cn.hutool.crypto.SmUtil.sm3("aaaaaaaaaaaaaaaa"));
//        System.out.println(cn.hutool.crypto.SmUtil.sm3("aaaaaaaaaaaaaaaa"));
//        System.out.println(cn.hutool.crypto.SmUtil.sm3("aaaaaaaaaaaaaaaa"));
//        System.out.println(cn.hutool.crypto.SmUtil.sm3("aaaaaaaaaaaaaaaa"));
//    }
//    @Test
//    void testDate(){
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = simpleDateFormat.parse("1900-01-01 08:00:00");
//            System.out.println(simpleDateFormat.format(date));
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
//    }

//    @Resource
//    private DictypeJsGenerator dictypeJsGenerator;
//    @Test
//    void testDictype() throws Exception{
//        ResultDTO resultDTO = dictypeJsGenerator.generateJsCode("commonDictype");
//        System.out.println(resultDTO.getData());
//    }
    @Test
    void testHump() throws Exception{
        String a="aaaa_asdfads_asdfa";
        String b=a.toUpperCase();
        System.out.println(StringTool.lineToHump(a));
        System.out.println(StringTool.lineToHump(b));

        System.out.println(StringTool.lineToHumpUpperCase(a));
        System.out.println(StringTool.lineToHumpUpperCase(b));

    }
    @Resource
    private RedisOnlineUserUtils redisOnlineUserUtils;
    @Test
    void testRedis() throws Exception{
        List<String> keys = redisOnlineUserUtils.findKeys("sys*");
        for(String s:keys){
            System.out.println(s);
        }
        List<String> keys1 = redisOnlineUserUtils.findKeys("sys_refresh_token_6*");
        for(String s:keys1){
            System.out.println(s);
        }
    }

    @Test
    void test1(){
        UserAgent userAgent = new UserAgent();
        System.out.println(userAgent.getBrowser());
        System.out.println(userAgent.getEngine());
        System.out.println(userAgent.getEngineVersion());
        System.out.println(userAgent.getOs());
        System.out.println(userAgent.getOsVersion());
        System.out.println(userAgent.getPlatform());
        System.out.println(userAgent.getVersion());
    }


}
