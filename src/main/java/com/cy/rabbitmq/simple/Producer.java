package com.cy.rabbitmq.simple;

import com.cy.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 简单模式：发送消息
 *
 * @Author：SatanCY
 * @Date：2024/9/27 17:16
 */
public class Producer {
    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        //1.创建连接工厂
        //2.创建链接
        Connection connection = RabbitUtils.getConnection();
        //3.创建频道
        Channel channel = connection.createChannel();
        //4.声明队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列（消息会持久化存储在服务器上）
         * 参数3：是否独占本连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：其他参数
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //5.发送消息
        String message = "Hello World";
        /**
         * 参数1：exchange（交换机名称）‌：指定消息要发送到的交换机。
         * 参数2：‌routingKey（路由键）‌：指定消息要路由到的队列，其设置与交换机的类型有关。
         * 参数3：‌props（消息属性）‌：消息的属性，通常设置为null。
         * 参数4：‌body（消息内容）‌：消息的实际内容，需要转换为字节数组进行传输。
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[X] send '" + message + "'");
        //6.关闭资源
        channel.close();
        connection.close();
    }
}
