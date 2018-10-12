package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.可靠性;

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
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/moke");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_confirm_exchange";
        String routeingKey = "confirm_save";
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare("test_confirm_queue",true,false,false,null);
        channel.queueBind("test_confirm_queue",exchangeName,routeingKey);
        
    }
}
