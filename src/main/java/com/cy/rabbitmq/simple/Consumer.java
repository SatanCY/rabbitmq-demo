package com.cy.rabbitmq.simple;

import com.cy.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author：SatanCY
 * @Date：2024/9/27 17:49
 */
public class Consumer {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] argv) throws Exception {
        //1.创建连接工厂（设置RabbitMQ的连接参数）
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
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //5.创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由key
                System.out.println("路由key为："+envelope.getRoutingKey());
                //交换机
                System.out.println("交换机为："+envelope.getRoutingKey());
                //消息id
                System.out.println("消息id为："+envelope.getDeliveryTag());
                //接收到的消息
                System.out.println("接收到的消息为："+new String(body,"UTF-8"));
            }
        };
        //6.监听队列
        /**
         * 参数1：队列名
         * 参数2：是否自动确认，设置为true时自动向MQ回复确认信息，MQ则会将消息从队列中删除，false则需要手动确认
         * 参数3：消费者
         */
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }
}
