package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test2;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huran
 * @Date: 2018/9/7 13:56
 * @Description:
 */
public class ProducerTest2 {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        String exchangeName = "confirmExchange1";
        String queueName = "confirmQueue1";
        String routingKey = "confirmRoutingKey1";
        String bindingKey = "confirmRoutingKey1";
        int count = 100;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);

        //创建生产者
        Sender producer = new Sender(factory, count, exchangeName, queueName,routingKey,bindingKey);
        producer.run();
    }

}
