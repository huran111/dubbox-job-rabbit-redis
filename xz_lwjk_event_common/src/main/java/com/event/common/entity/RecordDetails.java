package com.event.common.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author huran
 * @Date: 2018/8/11 12:47
 * @Description: 报文明细
 */
@Data
@Entity
@Table(name = "logger_record_details")

public class RecordDetails extends BaseEntity<String> {
    /**
     * 报文明细ID
     */
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;
    /**
     * 报文记录DI
     */
    @Column(name = "record_id")
    private String recordId;
    /**
     * 入参报文信息
     */
    @Column(name = "detail_msg")
    private String detailMsg;

    /**
     * 返回值报文信息
     */
    @Column(name = "return_msg")
    private String returnMsg;
}
