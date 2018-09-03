package com.xz.lwjk.event.facade.service;

import com.event.common.entity.Record;
import com.event.common.entity.RecordDetails;
import com.xz.lwjk.event.facade.common.service.IBaseService;

import java.util.Map;

/**
 * @author  系统集成使用
 * @Date: 2018/8/11 13:01
 * @Description:
 */
public interface IRecordService extends IBaseService<Record,String>{
    String saveOrUpdateRecord(Record record, RecordDetails recordDetails);
    Map<String,Object> findRecord(String recordId);
}
