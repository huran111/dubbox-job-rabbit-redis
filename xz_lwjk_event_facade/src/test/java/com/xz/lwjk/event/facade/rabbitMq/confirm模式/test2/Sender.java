package com.xz.lwjk.event.facade.rabbitMq.confirm模式.test2;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 13:57
 * @Description:
 */
public class Sender {
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
        try {
            //创建exchange
            channel.exchangeDeclare(exchangeName, "direct", true, false, null);
            //创建队列
            channel.queueDeclare(queueName, true, false, false, null);
            //绑定exchange和queue
            channel.queueBind(queueName, exchangeName, bindingKey);
            channel.confirmSelect();
            //发送持久化消息
            for (int i = 0; i < count; i++) {
                //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,
                //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话
                //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键
                channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_BASIC, ("第" + (i + 1) + "条消息").getBytes());
            }
            long start = System.currentTimeMillis();
            //设置监听
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("ack: deliveryTag = " + deliveryTag + " multiple: " + multiple);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("nack: deliveryTag = " + deliveryTag + " multiple: " + multiple);

                }
            });
            System.out.println("执行waitForConfirmsOrDie耗费时间: " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Thread.sleep(5000);
            channel.close();
            connection.close();
        }
    }
}
