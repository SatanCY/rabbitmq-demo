package com.cy.rabbitmq.routing;

import com.cy.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author：SatanCY
 * @Date：2024/10/7 22:23
 */
public class Producer {

    private static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    private static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";
    private static final String DIRECT_EXCHANGE = "direct_exchange";

    public static void main(String[] args) throws Exception {
        //1.创建连接
        Connection connection = RabbitUtils.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明交换机
        /**
         * 参数1：交换机名称
         * 参数2：交换机类型（fanout,direct,topic）
         */
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //4.声明队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列（消息会持久化存储在服务器上）
         * 参数3：是否独占本连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：其他参数
         */
        channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);
        //5.绑定队列
        /**
         * 参数1：队列名称
         * 参数2：交换机名称
         * 参数3：路由key
         */
        channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE,"insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE,"update");
        //6.发送10条消息
        String message1 = "Hello World 路由模式=== insert";
        String message2 = "Hello World 路由模式=== update";

        /**
         * 参数1：exchange（交换机名称）‌：指定消息要发送到的交换机。
         * 参数2：‌routingKey（路由键）‌：指定消息要路由到的队列，其设置与交换机的类型有关。
         * 参数3：‌props（消息属性）‌：消息的属性，通常设置为null。
         * 参数4：‌body（消息内容）‌：消息的实际内容，需要转换为字节数组进行传输。
         */
        channel.basicPublish(DIRECT_EXCHANGE,"insert",null,message1.getBytes());
        channel.basicPublish(DIRECT_EXCHANGE,"update",null,message2.getBytes());
        System.out.println("[X] send '" + message1 + "'");
        System.out.println("[X] send '" + message2 + "'");
        //7.关闭资源
        channel.close();
        connection.close();
    }
}
