package com.xz.lwjk.event.facade.rabbitMq.Direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author huran
 * @Date: 2018/9/7 10:39
 * @Description:
 */
public class EmitLogDirect {
    private static final String exchange_namae = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchange_namae, "direct");
            String bindKey = "aaa";
            String bindKeyb = "bbb";
            String message = "11";
            String collectionId = UUID.randomUUID().toString();
            String queueName = channel.queueDeclare().getQueue();
            //存入回调队列名与collectionId
            BasicProperties bpro = new BasicProperties().builder().correlationId(collectionId).replyTo(queueName).build();
        try {
            //事务模式
            channel.txSelect();
            channel.basicPublish(exchange_namae, bindKey, bpro, message.getBytes());
            channel.basicPublish(exchange_namae, bindKeyb, bpro, message.getBytes());
           // System.out.println("消息发送成功");

            if(channel.waitForConfirms()){
                System.out.println("消息发送成功");
            }
            //int result = 1 / 0;
            channel.txCommit();

        } catch (Exception e) {
            channel.txRollback();
        }finally {
           // channel.close();
           // connection.close();
        }

    }
}
