package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 12:36
 * @Description:
 */
public class ProducerTest {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        String exchangeName = "confirmExchange";
        String queueName = "confirmQueue";
        String routingKey = "confirmRoutingKey";
        String bindingKey = "confirmRoutingKey";
        int count = 100;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);

        //创建生产者
        Sender producer = new Sender(factory, count, exchangeName, queueName, routingKey, bindingKey);
        producer.run();
    }
}

class Sender {
    private ConnectionFactory factory;
    private int count;
    private String exchangeName;
    private String queueName;
    private String routingKey;
    private String bindingKey;

    public Sender(ConnectionFactory factory, int count, String exchangeName, String queueName, String routingKey, String bindingKey) {
        this.factory = factory;
        this.count = count;
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.bindingKey = bindingKey;
    }

    public void run() throws IOException, TimeoutException, InterruptedException {
        Channel channel = null;

            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            //创建exchange
            channel.exchangeDeclare(exchangeName, "direct", true, false, null);
            //创建队列
            channel.queueDeclare(queueName, true, false, false, null);
            //绑定exchange和queue
            channel.queueBind(queueName, exchangeName, bindingKey);
         //   channel.confirmSelect();
        try {
            //发送持久化消息
            for (int i = 0; i < count; i++) {
                //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,
                //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话
                //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键
                channel.txSelect();
                channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_BASIC, ("第" + (i + 1) + "条消息").getBytes());
               // int a=1/0;
                channel.txCommit();
                //仅仅将channel设置成confirm模式，并且生产者每发送一条消息就等待broker回应确认消息
               /* if (channel.waitForConfirms()) {
                    System.out.println("发送成功.....");
                } else {
                    System.out.println("发送失败.....");
                }*/
            }
            long start = System.currentTimeMillis();
           // channel.waitForConfirmsOrDie();
            System.out.println("执行waitForConfirmsOrDie耗费时间: " + (System.currentTimeMillis() - start) + "ms");

        } catch (Exception e) {
            channel.txRollback();
            e.printStackTrace();
        }finally {
            Thread.sleep(5000);
            channel.close();
            connection.close();
        }
    }

}
