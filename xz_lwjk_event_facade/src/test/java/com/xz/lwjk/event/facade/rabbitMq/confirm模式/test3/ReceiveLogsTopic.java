package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 14:27
 * @Description:
 */
public class ReceiveLogsTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("队列名称为:" + queueName);
        //接收所有的日志
        String bindKey = "#";
        //接收来自“kern”设施的所有日志:
        String bindKeyb = "kern.*";
        //“关键”日志:
        String bindKeyc = "*.critical";
       // String bindKeyc = "c";
        channel.queueBind(queueName, EXCHANGE_NAME, bindKey);
        channel.queueBind(queueName, EXCHANGE_NAME, bindKeyb);
        channel.queueBind(queueName, EXCHANGE_NAME, bindKeyc);

        // channel.queueBind(queueName, EXCHANGE_NAME, bindKeyc);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();
            System.out.println(" [x] routingKey '" + routingKey + "':'message:" + message + "'");
        }

    }
}
