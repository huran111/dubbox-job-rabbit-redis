package com.hr.entity;

import java.io.Serializable;

/**
 * @author huran
 * @Title: Order
 * @ProjectName rabbitmq-demo
 * @Description: TODO
 * @date 2018/9/39:56
 */
public class Order implements Serializable{
    private String id;
    private String name;
    private String messageId;

    public String getId() {
        return id;
    }
    public Order(){}
    public Order(String id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
