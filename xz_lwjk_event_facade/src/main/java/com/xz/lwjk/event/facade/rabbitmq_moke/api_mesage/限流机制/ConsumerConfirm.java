package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.限流机制;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
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
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName="test_qos_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);
        //限流方式
        //设置auto ACK 关闭自动签收false
        //0  不限制消息大小
        //3  一次推送三条  生产一般为 1
        //false 不设置channel级别
        channel.basicQos(0,1,false);
        channel.basicConsume(queueName,false ,new MyConsumer(channel));
    }
}
