package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.可靠性;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
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
        connectionFactory.setVirtualHost("/moke");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //执行消息的确认模式
        channel.confirmSelect();
        String exchangeName = "test_confirm_exchange";
        String routeingKey = "confirm.save";
        channel.basicPublish(exchangeName, routeingKey, null, "hello confirm message".getBytes());
        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.err.println("------ ack ------");
            }
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.err.println("------ no ack ------");

            }
        });

    }
}
