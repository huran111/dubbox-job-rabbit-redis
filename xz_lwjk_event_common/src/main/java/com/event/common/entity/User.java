package com.event.common.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: BUCHU
 * @Date: 2018/8/17 17:30
 * @Description:
 */
@Data
@Entity
@Table(name = "USER")
public class User extends BaseEntity<String>{
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;
    private String createBy;
    private Date createDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String userName;


}
