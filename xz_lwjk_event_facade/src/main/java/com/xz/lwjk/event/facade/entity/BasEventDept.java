package com.xz.lwjk.event.facade.entity;

import com.event.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author huran
 * @Date: 2018/8/9 20:28
 * @Description: 事件-部门
 */
@Data
@Entity
@Table(name = "BAS_EVENT_DEPT")
public class BasEventDept extends BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 事件Id
     */
    @Column(name = "event_id")
    private String eventId;

    /**
     * 联动部门id
     */
    private String deptId;
    /**
     * 联动部门名称
     */
    private Date deptName;
    /**
     * \发生时间
     */
    private Date happendTime;
    /**
     * 记录时间
     */
    private Date recordTime;
    /**
     * 通知时间
     */
    private Date noteTime;
    /**
     * 响应时间
     */
    private Date responseTime;
    /**
     * 到达时间
     */
    private Date arriveTime;
    /**
     * 处理时间
     */
    private Date handleTime;
    /**
     * 离开时间
     */
    private Date leaveTime;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 是否确认  0未确认1已确认
     */
    private Double isConfirm;
    /**
     * 状态节点
     */
    private String status;
}
