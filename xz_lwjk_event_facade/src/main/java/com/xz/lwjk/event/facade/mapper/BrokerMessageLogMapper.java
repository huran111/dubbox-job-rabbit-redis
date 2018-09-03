package com.xz.lwjk.event.facade.mapper;

import com.xz.lwjk.event.facade.model.BrokerMessageLog;

import java.util.Date;
import java.util.List;

public interface BrokerMessageLogMapper {
    int deleteByPrimaryKey(String messageId);

    int insert(BrokerMessageLog record);

    int insertSelective(BrokerMessageLog record);

    BrokerMessageLog selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(BrokerMessageLog record);

    int updateByPrimaryKey(BrokerMessageLog record);

    List<BrokerMessageLog> query4StatusAndTimeOut();

    void changeBrokerMessageLogStatus(String messageId, String orderSendFailure, Date date);

    void update4ReSend(String messageId, Date date);
}