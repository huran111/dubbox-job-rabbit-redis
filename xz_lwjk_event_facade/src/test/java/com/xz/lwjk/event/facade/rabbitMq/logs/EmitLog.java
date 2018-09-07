package com.xz.lwjk.event.facade.rabbitMq.logs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author huran
 * @Date: 2018/9/7 09:39
 * @Description:
 */
public class EmitLog {
    //定义交换器名称
    private static final String excange_name = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(excange_name, "fanout");
        String massage = UUID.randomUUID().toString();
        channel.basicPublish(excange_name,"",null,massage.getBytes());
        System.out.println("Send:"+massage);

        channel.close();
        connection.close();
    }
}
