package com.qiyuan.base2048.log;

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

import java.io.Serializable;

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
        System.out.println("-----====-日志log---=====--入参："+logInfo);
    }
}



