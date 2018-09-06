package com.xz.lwjk.event.facade.task;

import com.alibaba.fastjson.JSON;

import com.event.common.entity.Order;
import com.xz.lwjk.event.facade.constant.Constants;
import com.xz.lwjk.event.facade.mapper.BrokerMessageLogMapper;
import com.xz.lwjk.event.facade.mapper.OrderMapper;
import com.xz.lwjk.event.facade.model.BrokerMessageLog;
import com.xz.lwjk.event.facade.producer.RabbitOrderSend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author huran
 * @Title: RetryMessageTasker
 * @ProjectName rabbitmq-demo
 * @Description: TODO
 * @date 2018/9/316:03
 */
@Component
@Slf4j
public class RetryMessageTasker {
    @Autowired
    private RabbitOrderSend rabbitOrderSend;

    /**
     * 项目启动后3秒钟，每个十秒启动一次
     */
   // @Scheduled(initialDelay = 3000, fixedDelay = 20000)
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
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    //@Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void sendMessage(){
        log.info("==================>>>发送消息开始");
        Order order=new Order();
        order.setId(UUID.randomUUID().toString());
        order.setMessageId(UUID.randomUUID().toString());
        order.setName("胡冉");
        orderMapper.insert(order);
        BrokerMessageLog brokerMessageLog=new BrokerMessageLog();
        brokerMessageLog.setMessage(JSON.toJSONString(order));
        brokerMessageLog.setMessageId(order.getMessageId());
        brokerMessageLog.setStatus("0");
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(new Date(),Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insert(brokerMessageLog);
        rabbitOrderSend.senOrder(order);
    }
}
