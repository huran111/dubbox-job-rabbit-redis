package com.xz.lwjk.event.facade.mapper;

import com.xz.lwjk.event.facade.model.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;

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

    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status,@Param("updateTime") Date updateTime);

    void update4ReSend(@Param("messageId") String messageId,@Param("updateTime") Date updateTime);
}