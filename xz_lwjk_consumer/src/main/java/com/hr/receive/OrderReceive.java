package com.hr.receive;

import com.hr.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author huran
 * @Title: OrderReceive
 * @ProjectName rabbitmq-demo
 * @Description: TODO
 * @date 2018/9/312:36
 */
@Component
public class OrderReceive {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-quque", durable = "true"),
            exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"
            ), key = "order.*"
    ))
    @RabbitHandler
    public void onOrdermessage(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("开始消费：" + order.getMessageId());
        //手工签收
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(tag, false);
    }
}
