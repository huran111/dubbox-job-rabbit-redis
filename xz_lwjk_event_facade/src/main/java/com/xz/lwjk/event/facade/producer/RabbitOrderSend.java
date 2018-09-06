package com.xz.lwjk.event.facade.producer;

import com.event.common.entity.Order;
import com.xz.lwjk.event.facade.constant.Constants;
import com.xz.lwjk.event.facade.mapper.BrokerMessageLogMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author huran
 * @Title: OrderSend
 * @ProjectName rabbitmq-demo
 * @Description: TODO
 * @date 2018/9/315:33
 */
@Component
public class RabbitOrderSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.out.println("correlationData:" + correlationData);
            String messageId = correlationData.getId();
            System.out.println("消息ID为:"+messageId);
            if (ack) {
                //如果返回成功 则进行 更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS,new Date());
            } else {
                System.out.println("异常处理");
            }
        }
    };

    public void senOrder(Order order) {
        //设置监听回调函数
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlation = new CorrelationData();
       correlation.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.abcd", order, correlation);

    }
}
