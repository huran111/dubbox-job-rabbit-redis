/*
package com.xz.lwjk.event.facade.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.event.common.annotation.DataSourceType;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

*/
/**
 * @Auther: hr
 * @Date: 2018/8/4 20:51
 * @Description: 定义数据库实体类并为多数据源的形式
 *//*

//@Configuration
public class DatasourceConfig {

    @Bean(name = DataSourceType.MYSQL_DB)
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
    @Bean(name = DataSourceType.ORACLE_DB)
    @ConfigurationProperties(prefix = "spring.datasource1")
    public DataSource dataSourceSlave(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
}
*/
