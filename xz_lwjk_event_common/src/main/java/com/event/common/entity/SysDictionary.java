package com.event.common.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author huran
 * @Date: 2018/8/9 09:58
 * @Description: 系统字典表
 */
@Data
@Entity
@Table(name = "SYS_DICTIONARY")
public class SysDictionary extends BaseEntity<String> {
    /**
     * 主键
     */
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;

    /**
     * 字典代码
     */
    private String code;

    /**
     * 字典类型
     */
    @Column(name = "dic_type")
    private String dicType;
    /***
     * 父级-字典代码
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 排序
     */
    private Double sort;

}
