package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage;

import com.google.common.collect.Maps;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/10/10 16:25
 * @Description:
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/moke");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // 2---持久化消息
        //自定义属性
        Map<String,Object> headers=Maps.newHashMap();
        headers.put("huran","111");
        AMQP.BasicProperties  properties=new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentType("UTF-8")
                .expiration("10000")
                .headers(headers).build();
        for (int i = 0; i < 5; i++) {
        String msg="Hello RabbitMq";
        channel.basicPublish("","test0001",properties,msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
