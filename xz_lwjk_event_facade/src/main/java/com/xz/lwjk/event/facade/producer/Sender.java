package com.xz.lwjk.event.facade.producer;

import com.xz.lwjk.event.facade.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/4 09:47
 * @Description:
 */
@Component
public class Sender  implements RabbitTemplate.ConfirmCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public  void send(String context){
        rabbitTemplate.convertAndSend("spring-fanout-exchage","",context);
    }
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(" 回调id:" + correlationData);
        if (b) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + s);
        }
    }
}
