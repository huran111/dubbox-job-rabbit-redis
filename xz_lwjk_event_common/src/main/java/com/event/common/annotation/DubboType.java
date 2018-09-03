package com.event.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther: hr
 * @Date: 2018/8/4 20:04
 * @Description: 只做标识，不用做其他处理，表示为dubbo  protocol服务，
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DubboType {
    String value();
}
