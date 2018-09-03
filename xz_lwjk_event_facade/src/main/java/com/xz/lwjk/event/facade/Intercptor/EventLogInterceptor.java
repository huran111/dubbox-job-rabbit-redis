package com.xz.lwjk.event.facade.Intercptor;

import com.alibaba.fastjson.JSON;
import com.event.common.annotation.DubboType;
import com.event.common.dto.ApiResponseDTO;
import com.event.common.entity.Record;
import com.event.common.entity.RecordDetails;
import com.google.common.collect.Maps;
import com.xz.lwjk.event.facade.service.IRecordService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author Event日志拦截器
 * @Date: 2018/8/11 12:55
 * @Description:
 */
@Slf4j
@Aspect
@Component
public class EventLogInterceptor {
    @Autowired
    IRecordService recordService;

    /**
     * 连接controller层 以及 带有LwjcInterface注解的
     * && @annotation(com.event.common.annotation.DubboType))
     */
    @Pointcut("execution(* com.xz.lwjk.event.facade.controller..*(..)) && @annotation(com.event.common.annotation.DubboType)")
    public void EventLogMethdoPointcut() {

    }

    @Around("EventLogMethdoPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) {
        //组装调用前的相关报文表数据
        Map<String, Object> recordMap = combineRecord(pjp);
        //开始时间
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            if (result == null) {
                // 一切正常的情况下，继续执行被拦截的方法
                result = pjp.proceed();
            }
        } catch (Throwable e) {
            log.error("========================= ITRLogInterceptor:exception: ", e);
            //保存失败报文表信息
            saveRecordByFailure(recordMap, e);
            if (isDubboInterface(pjp)) {
                ApiResponseDTO dto = new ApiResponseDTO();
                dto.setCode("0");
                dto.setBusCode("0");
                dto.setMsg(e.getMessage());
                return dto;
            } else {
                return result;
            }
        }
        //结束时间
        long endTime = System.currentTimeMillis();
        //保存成功报文表信息
        saveRecordBySuccess(recordMap, endTime - startTime, result);

        return result;
    }

    /**
     * 判断是否是RPC接口
     *
     * @param pjp
     * @return true:RPC接口/false:其他接口
     */
    private boolean isDubboInterface(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        //通过接口的注解进行判断是否是REST接口还是RPC接口
        DubboType dubboRestType = method.getAnnotation(DubboType.class);
        if (dubboRestType == null) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 更新报文表信息（调用成功）
     *
     * @param recordMap 报文表和报文明细表数据Map
     * @param userTime  接口访问时间（毫秒）
     * @param result    接口访问返回值
     */
    private void saveRecordBySuccess(Map<String, Object> recordMap, long userTime, Object result) {
        //从数据库中获取报文表的
        Record record = (Record) recordMap.get("Record");
        RecordDetails recordDetail = (RecordDetails) recordMap.get("RecordDetail");
            record.setUseTime((int) userTime);
        if (result != null) {
            recordDetail.setReturnMsg(JSON.toJSONString(result));
        }
        recordService.saveOrUpdateRecord(record, recordDetail);
    }

    /**
     * 更新报文表信息（调用失败）
     *
     * @param recordMap 报文表和报文明细表数据Map
     * @param e         调用异常
     */
    private void saveRecordByFailure(Map<String, Object> recordMap, Throwable e) {
        //从数据库中获取报文表的
        Record record = (Record) recordMap.get("Record");
        RecordDetails recordDetail = (RecordDetails) recordMap.get("RecordDetail");
        record.setCode("0");
        record.setBusCode("0");
        record.setMsg("调用失败");
        recordDetail.setReturnMsg(e.getMessage());
        recordService.saveOrUpdateRecord(record, recordDetail);
    }

    /**
     * 组装调用之前的报文表和报文详细表相关数据
     *
     * @param pjp 拦截器的切点
     * @return 报文表Map，包含报文主表对象和报文明细表
     */
    private Map<String, Object> combineRecord(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        DubboType dubboType = method.getAnnotation(DubboType.class);
        log.info("========================= EventLogInterceptor:" + (dubboType != null ? dubboType.value() : ""));
        //获取被拦截的方法名
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
        Object[] args = pjp.getArgs();
        log.info("========================= EventLogInterceptor:" + JSON.toJSONString(args));

        //组装报文表和报文明细表的数据
        Record record = new Record();
        RecordDetails recordDetail = new RecordDetails();
        record.setCode("1");
        record.setBusCode("1");
        record.setMsg("调用成功");
        record.setInterfaceCode(dubboType != null ? dubboType.value() : "");
        record.setCreateDate(new Date());
        record.setMethodName(methodName);
        recordDetail.setDetailMsg(JSON.toJSONString(args));
        Map<String, Object> retMap = Maps.newHashMap();
        retMap.put("Record", record);
        retMap.put("RecordDetail", recordDetail);
        return retMap;
    }
}
