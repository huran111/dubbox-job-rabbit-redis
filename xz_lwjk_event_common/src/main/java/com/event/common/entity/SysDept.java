package com.event.common.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * @author huran
 * @Date: 2018/8/17 14:29
 * @Description:
 */
@Data
@Slf4j
@Entity
@Table(name = "SYS_DEPT")
public class SysDept  extends  BaseEntity{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Id;
    //部门编码
    private String Code;
    //部门名称
    private String name;
    //上级部门  关联表sys_dept
    private String superdep;
    //负责人  储存sys_user表中的用户ID 未做关联
    private String manager;
    //地址
    private String address;
    //是否可用  0:不可用;1:可用
    private String invalid;
    //简称
    private String shortname;
    private String sort;
    //状态
    private String state;
    //部门级别 1管理处，2路产科，3大队，4中队
    private String level;

}
