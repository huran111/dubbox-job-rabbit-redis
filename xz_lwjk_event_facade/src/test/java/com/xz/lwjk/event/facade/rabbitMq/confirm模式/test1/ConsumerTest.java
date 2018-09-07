package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test1;

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
public class ConsumerTest {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
       // channel.exchangeDeclare("confirmExchange","direct");
        channel.queueBind("confirmQueue", "confirmExchange", "confirmRoutingKey");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume("confirmQueue",false,consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received：" + message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }

}
