package com.xz.lwjk.event.facade.task;

import com.alibaba.fastjson.JSON;

import com.xz.lwjk.event.facade.constant.Constants;
import com.xz.lwjk.event.facade.mapper.BrokerMessageLogMapper;
import com.xz.lwjk.event.facade.model.BrokerMessageLog;
import com.xz.lwjk.event.facade.model.Order;
import com.xz.lwjk.event.facade.producer.RabbitOrderSend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author huran
 * @Title: RetryMessageTasker
 * @ProjectName rabbitmq-demo
 * @Description: TODO
 * @date 2018/9/316:03
 */
@Component
public class RetryMessageTasker {
    @Autowired
    private RabbitOrderSend rabbitOrderSend;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 项目启动后3秒钟，每个十秒启动一次
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend() {
        System.out.println("定时任务开始......");
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeOut();
        list.forEach(messageLog -> {
            if (messageLog.getTryCount() >= 3) {
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            } else {
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(), new Date());
                Order order = JSON.parseObject(messageLog.getMessage(), Order.class);
                try {
                    rabbitOrderSend.senOrder(order);
                } catch (Exception e) {
                    //逻辑
                }
            }
        });
    }
}
