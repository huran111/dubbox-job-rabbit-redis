package com.xz.lwjk.event.facade.entity;

import com.event.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Auther: BUCHU
 * @Date: 2018/8/16 12:49
 * @Description:
 */
@Table(name = "person")
@Entity
@Data
public class Person /*extends BaseEntity<Long>*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 4, min = 2)
    private String name;
    private String age;
    private String nation;
    private String address;


}
