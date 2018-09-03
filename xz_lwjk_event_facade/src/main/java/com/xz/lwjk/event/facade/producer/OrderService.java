package com.xz.lwjk.event.facade.producer;

import com.alibaba.fastjson.JSON;

import com.xz.lwjk.event.facade.constant.Constants;
import com.xz.lwjk.event.facade.mapper.BrokerMessageLogMapper;
import com.xz.lwjk.event.facade.mapper.OrderMapper;
import com.xz.lwjk.event.facade.model.BrokerMessageLog;
import com.xz.lwjk.event.facade.model.Order;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author BUCHU
 * @Title: OrderService
 * @ProjectName rabbitmq-demo
 * @Description: TODO
 * @date 2018/9/315:55
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    @Autowired
    private RabbitOrderSend orderSend;

    public void createOrder(Order order) {
        Date orderTime = new Date();
        orderMapper.insert(order);
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(order.getMessageId());
        brokerMessageLog.setMessageId(JSON.toJSONString(order));
        brokerMessageLog.setStatus("0");
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insert(brokerMessageLog);
        orderSend.senOrder(order);
    }
}
