package com.xz.lwjk.event.facade.Intercptor;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.event.common.entity.LoggerEntity;
import com.event.common.utils.ClientUtil;
import com.event.common.utils.DataUtils;
import com.xz.lwjk.event.facade.repository.LoggerEntityRespository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: huran
 * @Date: 2018/8/10 09:25
 * @Description:
 */
@Slf4j
//@Component
@Transactional(rollbackFor = Exception.class)
public class LoggerIntercptor implements HandlerInterceptor {
    private static final String LOGGER_SEND_TIME = "_send_time";
    private static final String LOGGER_ENTITY = "_logger_entity";
    @Autowired
    LoggerEntityRespository loggerEntityRespository;

    /**
     * 进入Spring MVC的controller之前开始记录日志实体
     *
     * @param request
     * @param response
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器开始执行......................[{}]", "preHandle");
        LoggerEntity logger = new LoggerEntity();

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            logger.setMethodName(methodName);
            String a = getParamString(request.getParameterMap());
            System.out.println(a);

        }
        String sessionId = request.getRequestedSessionId();
        String uri = request.getRequestURI();
        Map<String, String[]> map = request.getParameterMap();
        String paramData = JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
        logger.setClientIp(ClientUtil.getIpAddress(request));
        logger.setMethod(request.getMethod());
        logger.setType(ClientUtil.isExistsAjax(request));
        logger.setParamData(paramData);
        logger.setUri(uri);
        logger.setSessionId(sessionId);
        request.setAttribute(LOGGER_SEND_TIME, System.currentTimeMillis());
        request.setAttribute(LOGGER_ENTITY, logger);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("拦截器开始执行......................[{}]", "postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        log.info("拦截器开始执行......................[{}]", "afterCompletion");
        int status = response.getStatus();
        Thread.sleep(1200);
        long currentTime = System.currentTimeMillis();
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());

        LoggerEntity loggerEntity = (LoggerEntity) request.getAttribute(LOGGER_ENTITY);
        loggerEntity.setTime(DataUtils.getHMS(currentTime - time));
        loggerEntity.setReturnTime(currentTime + "");
        loggerEntity.setHttpStatusCode(String.valueOf(status));
        // loggerEntity.setReturnData(JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue));
        loggerEntityRespository.save(loggerEntity);

    }

    /**
     * 这个工具类可以通过HttpServletRequest请求对象的上下文
     * （ServetCotext）获取Spring管理的Bean
     *
     * @param clazz
     * @param request
     * @param <T>
     */
    public <T> T getDao(Class<T> clazz, HttpServletRequest request) {
        BeanFactory beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return beanFactory.getBean(clazz);
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

}
