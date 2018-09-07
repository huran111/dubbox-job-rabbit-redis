package com.xz.lwjk.event.facade.rabbitMq.Direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 10:47
 * @Description:
 */
public class ReceiveLogsDirect {
    private static final String exchange_name = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(exchange_name,"direct");
        String queuename=channel.queueDeclare().getQueue();
        System.out.println("队列名称为:"+queuename);
        channel.queueBind(queuename,exchange_name,"aaa");
        channel.queueBind(queuename,exchange_name,"bbb");
        QueueingConsumer consumer=new QueueingConsumer(channel);
        boolean ack=true;
        channel.basicConsume(queuename,false,consumer);
        while (true){
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
           String message=new String(delivery.getBody());
            String routing=delivery.getEnvelope().getRoutingKey();
            System.out.println("routingKey:"+routing+":"+message);
        }

    }
}
