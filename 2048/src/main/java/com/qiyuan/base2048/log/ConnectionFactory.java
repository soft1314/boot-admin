package com.qiyuan.base2048.log;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.qiyuan.bautil.util.ReadYmlUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private DruidDataSource dataSource;
    private static ConnectionFactory connectionFactory;

    private Connection getConnection(Properties properties) throws SQLException {
        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            try {
                init();
            } catch (Exception e2) {
            }
        }
        return dataSource.getConnection();
    }

    public static Connection getDatabaseConnection() throws SQLException {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        // 读取配置，只能用原生的方式读
        String driverClassName = ReadYmlUtil.getProfileValueToString("spring.datasource.driver-class-name");
        String url = ReadYmlUtil.getProfileValueToString("spring.datasource.url");
        String username = ReadYmlUtil.getProfileValueToString("spring.datasource.username");
        String password = ReadYmlUtil.getProfileValueToString("spring.datasource.password");

        Properties properties = new Properties();
        properties.put("driverClassName", driverClassName);
        properties.put("url", url);
        properties.put("username", username);
        properties.put("password", password);

        return connectionFactory.getConnection(properties);
    }

    public void init() {
        try {
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (Exception e) {
        }
    }
}
