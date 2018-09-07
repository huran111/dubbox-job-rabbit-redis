package com.xz.lwjk.event.facade.rabbitMq.回调;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 11:17
 * @Description:
 */
public class RpcReceiver {
    private static String requestQueueName = "rpc_queue";

    public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        Channel channel = connection.createChannel();
        channel.queueDeclare(requestQueueName, false, false, false, null);
        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列 打开ack机制
        channel.basicConsume(requestQueueName, false, consumer);
        while (true) {
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("收到客户端发来的消息:" + message);
            //获取回调队列名与Correlation Id
            BasicProperties bpro = delivery.getProperties();
            String replName = bpro.getReplyTo();
            System.out.println("服务端队列名称:"+replName);
            BasicProperties replBP = new BasicProperties().builder().correlationId(bpro.getCorrelationId()).build();
          System.out.print("服务端的getCorrelationId"+ delivery.getProperties().getCorrelationId());
            String responseMsg = "Just Do It";
            channel.basicPublish("", replName, replBP, responseMsg.getBytes());
            //发送应答，通过delivery.getEnvelope().getDeliveryTag()获取此次确认的消息的序列号
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
