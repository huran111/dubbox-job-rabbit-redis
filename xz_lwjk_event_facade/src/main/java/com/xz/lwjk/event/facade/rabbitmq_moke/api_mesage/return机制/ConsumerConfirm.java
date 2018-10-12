package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.return机制;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author  胡小白
 * @Date: 2018/10/11 13:01
 * @Description:
 */
public class ConsumerConfirm {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/hudabai");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_return_exchange";
        String routingKey = "return.#";
        String queueName="test_return_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);
        //设置自动签收 --true
        channel.basicConsume(queueName,true,queueingConsumer);
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg=new String (delivery.getBody());
            System.out.println("========================>>>"+msg);
        }
    }
}
