package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.限流机制;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Auther: 胡小白
 * @Date: 2018/10/11 18:43
 * @Description: 自定义监听
 */
public class MyConsumer extends DefaultConsumer {
    private Channel channel;
    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("----------message  ----------");
        System.out.println("consumerTag：" + consumerTag);
        System.out.println("envelope：" + envelope);
        System.out.println("properties：" + properties);
        System.out.println("msg:" + new String(body));
        //false 不支持批量签收
          //channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
