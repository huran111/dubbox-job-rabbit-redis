package com.xz.lwjk.event.facade.service.impl;

import com.event.common.entity.Record;
import com.event.common.entity.RecordDetails;
import com.google.common.collect.Maps;
import com.xz.lwjk.event.facade.common.service.impl.BaseServiceImpl;
import com.xz.lwjk.event.facade.repository.RecordDetailsRepository;
import com.xz.lwjk.event.facade.repository.RecordRepository;
import com.xz.lwjk.event.facade.service.IRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Transactional(rollbackFor = Exception.class)
@Service(value = "recordService")
public class RecordServiceImpl extends BaseServiceImpl<Record, String> implements IRecordService {
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    RecordDetailsRepository recordDetailsRepository;



    @Override
    public String saveOrUpdateRecord(Record record, RecordDetails recordDetails) {
        //判断是新增还是更新
        if (StringUtils.isNotBlank(record.getId())) {
            recordRepository.save(record);
            recordDetailsRepository.save(recordDetails);
            return record.getId();
        } else {
            recordRepository.save(record);
            recordDetails.setRecordId(record.getId());
            recordDetailsRepository.save(recordDetails);
            return record.getId();
        }
    }


    @Override
    public Map<String, Object> findRecord(String recordId) {
        Record record = recordRepository.findById(recordId);
        RecordDetails recordDetails = recordDetailsRepository.findById(recordId);
        Map<String, Object> retMap = Maps.newHashMap();


        retMap.put("Record", record);
        retMap.put("RecordDetails", recordDetails);
        return retMap;
    }
}
