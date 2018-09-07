package com.xz.lwjk.event.facade.rabbitMq.回调;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 11:16
 * @Description:
 */
public class RPCClient {
    private static String requestQueueName = "rpc_queue";
    private static String replyQueueName;
    public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //若此处不声明队列，则消息发送者先运行而队列未声明时消息会丢失。
        //channel.queueDeclare(requestQueueName, false, false, false, null);
        replyQueueName = channel.queueDeclare().getQueue();
        System.out.println("replyQueueName:"+replyQueueName);
        String message = "Welcome MuYi";
        String collectionId = UUID.randomUUID().toString();

        System.out.println("客户端:唯一ID:"+collectionId);
        //存入回调队列名与collectionId
        BasicProperties bpro = new BasicProperties().builder().correlationId(collectionId).replyTo(replyQueueName).build();
        channel.basicPublish("", requestQueueName, bpro, message.getBytes());
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列 关闭ack机制
        channel.basicConsume(replyQueueName, true, consumer);
        String responseMsg = null;
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if(collectionId.equals(delivery.getProperties().getCorrelationId())){
                responseMsg = new String(delivery.getBody());
                break;
            }
        }
        System.out.println("客户端接收到服务端发来的消息: "+responseMsg);

    }
}
