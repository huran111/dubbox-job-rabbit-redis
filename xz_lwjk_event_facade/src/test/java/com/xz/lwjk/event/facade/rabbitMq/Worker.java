package com.xz.lwjk.event.facade.rabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/6 15:48
 * @Description:
 */
public class Worker {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         durable：true、false true：在服务器重启时，能够存活
         exclusive ：是否为当前连接的专用队列，在连接断开后，会自动删除该队列，生产环境中应该很少用到吧。
         autodelete：当没有任何消费者使用时，自动删除该队列
         */

        boolean durable = true;
        channel.queueDeclare("hello", durable, false, false, null);
        System.out.println("=======================>>>>>>接收消息");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        /**
         * autoAck：是否自动ack，如果不自动ack，需要使用channel.ack、channel.nack、channel.basicReject 进行消息应答
         */
        boolean autoAck = false;
        channel.basicConsume("hello", autoAck, consumer);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) {
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        String message = new String(delivery.getBody());
                        try {
                            /**
                             * deliveryTag:该消息的index
                             multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                             */
                            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "接收到的消息为:" + message);


                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
