package com.event.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hr
 * 切库注解类 默认为MYSQL
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RoutingDataSource {
    String value() default DataSourceType.MYSQL_DB;

}
