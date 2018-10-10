package com.xz.lwjk.event.facade.rabbitmq_moke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huran
 * @Title: Producer4DirectExchange
 * @ProjectName rabbitmq
 * @Description: TODO
 * @date 2018/10/1013:35
 */
public class Consumer4DirectExchange {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/moke");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //创建连接
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routeKey = "test_direct";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
       //生命一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        //建立一个绑定的关系
        channel.queueBind(queueName, exchangeName, routeKey);
        //durable是否持久化消息
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while (true){
            //获取消息如果没有消息将会一直阻塞
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("收到消息："+msg);
        }
    }
}
