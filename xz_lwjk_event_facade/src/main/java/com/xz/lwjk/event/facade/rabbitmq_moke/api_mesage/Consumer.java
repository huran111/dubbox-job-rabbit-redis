package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.amqp.rabbit.support.Delivery;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/10/10 16:25
 * @Description:
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/moke");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queue="test0001";
        channel.queueDeclare(queue,true,false,false,null);
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);
        channel.basicConsume(queue,true,queueingConsumer);
        while (true){
            QueueingConsumer.Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String (delivery.getBody());
            System.out.println("消息消费:"+msg);
           Map<String ,Object> map= delivery.getProperties().getHeaders();
           String a= (String) map.get("huran").toString();
           System.out.println(a);
        }


    }
}
