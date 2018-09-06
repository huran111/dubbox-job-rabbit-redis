package com.xz.lwjk.event.facade;

import com.event.common.entity.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.xz.lwjk.event.facade.mapper.BrokerMessageLogMapper;
import com.xz.lwjk.event.facade.mapper.OrderMapper;
import com.xz.lwjk.event.facade.producer.RabbitOrderSend;
import com.xz.lwjk.event.facade.producer.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.Query;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XzLwjkEventFacadeApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    @Autowired
    private RabbitOrderSend rabbitOrderSend;

    @Test
    public void sendMessage() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            //Thread.sleep(2000);
            System.out.println("==================>>>发送消息开始");
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setMessageId(UUID.randomUUID().toString());
            order.setName("胡冉");
    /*		orderMapper.insert(order);
        BrokerMessageLog brokerMessageLog=new BrokerMessageLog();
		brokerMessageLog.setMessage(JSON.toJSONString(order));
		brokerMessageLog.setMessageId(order.getMessageId());
		brokerMessageLog.setStatus("0");
		brokerMessageLog.setNextRetry(DateUtils.addMinutes(new Date(), Constants.ORDER_TIMEOUT));
		brokerMessageLog.setCreateTime(new Date());
		brokerMessageLog.setUpdateTime(new Date());
		brokerMessageLogMapper.insert(brokerMessageLog);*/
            rabbitOrderSend.senOrder(null);
        }
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendHello() {
        String a = "aaa" + new Date();
        rabbitTemplate.convertAndSend("helloQueue", a);
    }

    @Autowired
    Sender sender;

    @Test
    public void sendFanout() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            sender.send("胡冉");
        }
        Thread.sleep(1000000);
    }

    private final static String QUEUE_NAME = "hello";

    @Test
    public void send() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         queue：QUEUE_NAME 队列名称
         durable：是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
         exclusive：是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，如果是排外的，会对当前队列加锁，其他通道channel是不能访问
         autoDelete：是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除，可以通过RabbitMQ Management，查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
         arguments：
         队列中的消息什么时候会自动被删除？
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        /**
         routingKey：路由键，#匹配0个或多个单词，*匹配一个单词，在topic exchange做消息转发用

         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("send:" + message);
        channel.close();
        connection.close();
    }

    @Test
    public void receive() throws IOException, TimeoutException, InterruptedException {
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
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("=======================>>>>>>接收消息");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        /**
         * autoAck：是否自动ack，如果不自动ack，需要使用channel.ack、channel.nack、channel.basicReject 进行消息应答
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true){
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String message=new String(delivery.getBody());
            System.out.println("接收到的消息为:"+message);

        }
    }
}
