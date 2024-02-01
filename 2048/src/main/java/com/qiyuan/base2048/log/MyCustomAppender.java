package com.qiyuan.base2048.log;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.qiyuan.base2048.feign.FeignLogService;
import com.qiyuan.base2048.service.component.GetBeanUtils;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.StringContainerVO;
import com.qiyuan.bautil.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.cloud.client.ServiceInstance;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Plugin(name = "MyCustomAppender", category = "Core", elementType = "appender", printObject = true)
public class MyCustomAppender extends AbstractAppender {
    public MyCustomAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions,
                           String registryAddress, String registryProtocol, String namespace, String protocolName, String consumerGroup) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent logEvent) {
        final byte[] bytes = getLayout().toByteArray(logEvent);
        String  log= new String(bytes);
        doLog(log);
    }

    // 下面这个方法可以接收配置文件中的参数信息
    @PluginFactory
    public static MyCustomAppender createAppender(
            @PluginAttribute("name") String name, @PluginElement("Filter") final Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
            @PluginAttribute("registryAddress") String registryAddress,
            @PluginAttribute("registryProtocol") String registryProtocol,
            @PluginAttribute("namespace") String namespace,
            @PluginAttribute("protocolName") String protocolName,
            @PluginAttribute("consumerGroup") String consumerGroup
    ) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new MyCustomAppender(name, filter, layout, ignoreExceptions,registryAddress,registryProtocol,namespace,protocolName,consumerGroup);
    }
    /**
     * 日志处理逻辑
     * @param logInfo
     */
    public void doLog(String logInfo){
        if(StringUtil.isBlank(logInfo)){
            return;
        }
        StringContainerVO stringContainerVO = new StringContainerVO();
        stringContainerVO.setStrValue(logInfo);
        FeignLogService feignLogService = GetBeanUtils.getFeignBean("feignLogService",FeignLogService.class);
        if(feignLogService != null) {
            try {
                NacosServiceDiscovery nacosServiceDiscovery = GetBeanUtils.getFeignBean("nacosServiceDiscoverry",NacosServiceDiscovery.class);
                List<ServiceInstance> serviceInstances = nacosServiceDiscovery.getInstances("service-rest-log6144");
                if(serviceInstances != null && serviceInstances.size()>0) {
                    ResultDTO resultDTO = feignLogService.sendLog(stringContainerVO);
                }else{
                    System.out.println("未发现日志服务！");
                }
//            log.debug(resultDTO.getMessage());
            }catch (RuntimeException runtimeException){
                System.out.println(runtimeException.getMessage());
            }catch (Exception ex){
                System.out.println(ex.getMessage());

            }
        }
    }
}



