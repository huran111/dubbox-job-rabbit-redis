package com.xz.lwjk.event.facade.rabbitmq_moke.api_mesage.return机制;

import com.rabbitmq.client.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author  胡小白
     * @Date: 2018/10/11 12:57
 * @Description:
 */
public class ProducerConfirm {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/hudabai");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //交换器名称
        String exchangeName = "test_return_exchange";
        //正确的路由键
        String routingKey = "return.save";
        //错误的路由键
        String routingErrorKey = "abc.save";
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routeingKey, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
                System.out.println("=====================  reutrn ====================");
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routeingKey:"+routeingKey);
                System.out.println("routeingKey:"+basicProperties);
                System.out.println("body:"+new String (body));
            }
        });
        //mandatory 为true
        //channel.basicPublish(exchangeName, routingKey, true, null, "hello return message".getBytes());
        //发送到 error key
        channel.basicPublish(exchangeName, routingErrorKey, true, null, "hello return message".getBytes());
    }
}
