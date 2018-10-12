package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.限流机制;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/10/11 12:57
 * @Description:
 */
public class ProducerConfirm {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/hudabai");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //交换器名称
        String exchangeName = "test_qos_exchange";
        //正确的路由键
        String routingKey = "qos.save";
        String msg = "hello";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, true, null , (msg+i).getBytes());
        }

    }
}
