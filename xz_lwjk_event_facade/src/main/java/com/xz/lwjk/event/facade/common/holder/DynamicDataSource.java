package com.xz.lwjk.event.facade.common.holder;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: BUCHU
 * @Date: 2018/8/4 21:06
 * @Description:
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("数据源为:"+DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();
    }
}
