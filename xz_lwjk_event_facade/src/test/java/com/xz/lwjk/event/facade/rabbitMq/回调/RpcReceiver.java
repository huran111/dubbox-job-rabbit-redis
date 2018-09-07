package com.xz.lwjk.event.facade.rabbitMq.回调;

import com.rabbitmq.client.*;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 11:17
 * @Description:
 */
public class RpcReceiver {
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        String exchangeName = "rpc_exchange";   //交换器名称
        String queueName = "rpc_queue";     //队列名称
        String routingKey = "rpc_key";  //路由键

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("test");
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();    //创建链接

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "topic", false, false, null);    //定义交换器

        channel.queueDeclare(queueName, false, false, false, null); //定义队列

        channel.queueBind(queueName, exchangeName, routingKey, null); //绑定队列

        QueueingConsumer consumer = new QueueingConsumer(channel);     //创建一个消费者

        channel.basicConsume(queueName, true, consumer);  //消费消息

        while (true) {

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  //获得一条消息

            String correlationID = delivery.getProperties().getCorrelationId();    //获得额外携带的correlationID

            String replyTo = delivery.getProperties().getReplyTo(); //获得回调的队列路由键

            String body = (String) SerializationUtils.deserialize(delivery.getBody());  //获得请求的内容
            System.out.println("客户端发送的内容:"+body);
            String responseMsg = "welcome " + body; //处理返回内容

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .correlationId(correlationID)   //返回消息时携带 请求时传过来的correlationID
                    .build();

            channel.basicPublish("", replyTo, properties, SerializationUtils.serialize(responseMsg)); //返回处理结果

        }
    }
}
