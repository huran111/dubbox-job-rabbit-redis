package com.xz.lwjk.event.facade.common.holder;

import com.event.common.annotation.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther: hr
 * @Date: 2018/8/4 21:08
 * @Description:
 */

public class DataSourceContextHolder {
    static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);
    public static final String DUFAULT_DATASOURCE = DataSourceType.MYSQL_DB;
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDB(String dbType) {
        logger.info("切换{}数据源", dbType);
        contextHolder.set(dbType);
    }

    public static String getDB() {
        return contextHolder.get();
    }

    //删除数据源名称
    public static void clearDB() {
        contextHolder.remove();
    }
}
