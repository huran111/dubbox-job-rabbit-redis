package com.xz.lwjk.event.facade.rabbitMq.logs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huran
 * @Date: 2018/9/7 09:49
 * @Description:
 */
public class ReceiveLogs {
    private static final String exchange_name = "logs";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_name, "fanout");
        String queuueName = channel.queueDeclare().getQueue();
        System.out.println("队列名称............:" + queuueName);
        String bindingKey = "";
        channel.queueBind(queuueName, exchange_name, bindingKey);
        System.out.println("==========================>>>等待消费");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queuueName, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received：" + message);
        }
    }
}
