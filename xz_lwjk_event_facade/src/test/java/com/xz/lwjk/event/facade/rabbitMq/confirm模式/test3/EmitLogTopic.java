package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huran
 * @Date: 2018/9/7 14:22
 * @Description:
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String routingKey = "topic-logs";
        String routingKey2 = "kern-logs";
        String routingKey3 = "kern-logs11111";
        String routingKey4 = "kern.critical";

        String queueName = "topic-logs";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, queueName.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey2, null, queueName.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey3, null, queueName.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey4, null, queueName.getBytes());

        channel.close();
        connection.close();
    }

}
