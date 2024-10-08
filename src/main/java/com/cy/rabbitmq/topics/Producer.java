package com.cy.rabbitmq.topics;

import com.cy.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author：SatanCY
 * @Date：2024/10/7 22:23
 */
public class Producer {

    private static final String TOPICS_QUEUE_1 = "topics_queue_1";
    private static final String TOPICS_QUEUE_2 = "topics_queue_2";
    private static final String TOPICS_EXCHANGE = "topics_exchange";

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
        channel.exchangeDeclare(TOPICS_EXCHANGE, BuiltinExchangeType.TOPIC);
        //4.声明队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列（消息会持久化存储在服务器上）
         * 参数3：是否独占本连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：其他参数
         */
        channel.queueDeclare(TOPICS_QUEUE_1, true, false, false, null);
        channel.queueDeclare(TOPICS_QUEUE_2, true, false, false, null);
        //5.绑定队列
        /**
         * 参数1：队列名称
         * 参数2：交换机名称
         * 参数3：路由key
         */
        channel.queueBind(TOPICS_QUEUE_1, TOPICS_EXCHANGE,"item.insert");
        channel.queueBind(TOPICS_QUEUE_1, TOPICS_EXCHANGE,"item.update");
        channel.queueBind(TOPICS_QUEUE_2, TOPICS_EXCHANGE,"item.#");
        //6.发送消息
        String message1 = "Hello World 通配符模式=== insert";
        String message2 = "Hello World 通配符模式=== update";
        String message3 = "Hello World 通配符模式=== delete";

        /**
         * 参数1：exchange（交换机名称）‌：指定消息要发送到的交换机。
         * 参数2：‌routingKey（路由键）‌：指定消息要路由到的队列，其设置与交换机的类型有关。
         * 参数3：‌props（消息属性）‌：消息的属性，通常设置为null。
         * 参数4：‌body（消息内容）‌：消息的实际内容，需要转换为字节数组进行传输。
         */
        channel.basicPublish(TOPICS_EXCHANGE,"item.insert",null,message1.getBytes());
        channel.basicPublish(TOPICS_EXCHANGE,"item.update",null,message2.getBytes());
        channel.basicPublish(TOPICS_EXCHANGE,"item.delete",null,message3.getBytes());
        System.out.println("[X] send '" + message1 + "'");
        System.out.println("[X] send '" + message2 + "'");
        System.out.println("[X] send '" + message3 + "'");
        //7.关闭资源
        channel.close();
        connection.close();
    }
}
