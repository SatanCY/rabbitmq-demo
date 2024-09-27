package com.cy.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @Author：SatanCY
 * @Date：2024/9/27 18:02
 */
public class RabbitUtils {

    public static Connection getConnection() throws Exception {
        //1.创建连接工厂（设置RabbitMQ的连接参数）
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 主机：默认localhost
        connectionFactory.setHost("localhost");
        // 连接端口：默认5672
        connectionFactory.setPort(5672);
        // 虚拟主机：默认/
        connectionFactory.setVirtualHost("/item");
        // 用户名：默认guest
        connectionFactory.setUsername("sanwei");
        // 密码：默认guest
        connectionFactory.setPassword("sanwei");
        //2.创建链接并返回
        return connectionFactory.newConnection();
    }

}
