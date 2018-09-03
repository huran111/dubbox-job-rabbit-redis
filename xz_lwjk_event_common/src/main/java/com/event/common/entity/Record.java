package com.event.common.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 报文记录
 * @Date: 2018/8/11 12:43
 * @Description:
 */
@Data
@Entity
@Table(name = "logger_record")

public class Record extends BaseEntity<String> {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;
    /**
     * 接口编号
     */
    @Column(name = "interface_code")
    private String interfaceCode;
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 用时
     */
    @Column(name = "use_time")
    private Integer useTime;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 预留字段
     */
    @Column(name = "bus_code")
    private String busCode;
    /**
     * 方法名
     */
    @Column(name = "method_name")
    private String methodName;
}
