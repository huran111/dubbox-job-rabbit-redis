package com.xz.lwjk.event.facade.rabbitMq.回调;

import com.rabbitmq.client.*;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/7 11:16
 * @Description:
 */
public class RPCClient {
    public String sayHelloToServer(String username) throws IOException, TimeoutException, InterruptedException {
        //交换器名称
        String exchangeName = "rpc_exchange";
        //队列名称
        String queueName = "rpc_queue";
        //路由键
        String routing = "rpc_key";
        //创建链接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("test");
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //定义交换器
        channel.exchangeDeclare(exchangeName, "topic", false, false, null);
        //定义队列
        channel.queueDeclare(queueName, false, false, false, null);
        //绑定队列
        channel.queueBind(queueName, exchangeName, routing, null);
        //获得匿名的 独立的 默认队列
        String callbackQueue = channel.queueDeclare().getQueue();
        //产生一个 关联ID correlationID
        String correlationId = UUID.randomUUID().toString();
        // 创建一个消费者对象
        QueueingConsumer consumer = new QueueingConsumer(channel);
//消费消息
        channel.basicConsume(callbackQueue, true, consumer);
        //创建消息属性
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                .correlationId(correlationId)   //携带唯一的 correlationID
                .replyTo(callbackQueue).build();    //携带callback 回调的队列路由键
        channel.basicPublish(exchangeName,routing,basicProperties, SerializationUtils.serialize(username));  //发布消息
        String response = null;
        while(true){

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();   //循环获得消息

            if(correlationId.equals(delivery.getProperties().getCorrelationId())){  //匹配correlationID是否与发出去的correlation的ID一直

                response = (String) SerializationUtils.deserialize(delivery.getBody()); //获得处理结果

                break;
            }

        }


        channel.close();

        connection.close();


        //关闭链接

        return response;
    }

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {

        RPCClient client = new RPCClient();

        String response = client.sayHelloToServer("TONY");

        System.out.println("server response : " + response);

    }
}
