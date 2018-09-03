package com.event.common.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author huran
 * @Date: 2018/8/10 09:14
 * @Description: 记录日志表
 */
@Entity
@Data
@Table(name = "logger_info")

public class LoggerEntity extends BaseEntity<String> {
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;

    /**
     * 请求客户端ip
     */
    @Column(name = "client_ip")
    private String clientIp;

    /**
     * 请求路径
     */
    private String uri;

    /***
     * 请求类型
     */
    private String type;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求的参数内容
     */
    @Column(name = "param_data")
    private String paramData;

    /**
     * 请求接口的唯一Session Id
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 请求时间
     */
    @Column(name = "time",insertable = true)
    private String time;

    /**
     * 接口返回时间
     */
    @Column(name = "return_time")
    private String returnTime;

    /**
     * 接口返回数据
     */
    @Column(name = "return_data")
    private String returnData;

    /**
     *状态码
     */
    @Column(name = "http_status_code")
    private String  httpStatusCode;

    /**
     *
     */
    @Column(name = "method_name")
    private String methodName;
}
