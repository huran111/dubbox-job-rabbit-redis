package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 12:39
 * @Description:
 */
public class ConsumerTest1 {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
       // channel.exchangeDeclare("confirmExchange","direct");
        channel.queueBind("confirmQueue1", "confirmExchange1", "confirmRoutingKey1");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume("confirmQueue1",false,consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received1：" + message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }

}
