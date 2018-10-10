package com.xz.lwjk.event.facade.rabbitmq_moke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huran
 * @Title: Producer4DirectExchange
 * @ProjectName rabbitmq
 * @Description: TODO
 * @date 2018/10/1013:35
 */
public class Producer4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory   connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/moke");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //创建连接
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        String exchangeName="test_direct_exchange";
        String routingKey="test_direct";
        String msg="hello world rabitmq 4 direct exchange message";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

    }
}
