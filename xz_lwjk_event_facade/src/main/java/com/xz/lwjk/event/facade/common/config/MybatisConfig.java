/*
package com.xz.lwjk.event.facade.common.config;

import com.event.common.annotation.DataSourceType;
import com.google.common.collect.Maps;
import com.xz.lwjk.event.facade.common.holder.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

*/
/**
 * @Auther:
 * @Date: 2018/8/4 21:00
 * @Description:配置成动态数据源
 *//*

@Configuration
@MapperScan(basePackages = "")
public class MybatisConfig {
    @Autowired
    @Qualifier(DataSourceType.MYSQL_DB)
    private DataSource mysqlDb;
    @Autowired
    @Qualifier(DataSourceType.ORACLE_DB)
    private DataSource oracleDb;
    */
/**
     * 动态数据源
     *//*

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource =new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(mysqlDb);
        Map<Object,Object> dbMap= Maps.newHashMap();
        dbMap.put(DataSourceType.MYSQL_DB,mysqlDb);
        dbMap.put(DataSourceType.ORACLE_DB,oracleDb);
        dynamicDataSource.setTargetDataSources(dbMap);
        return dynamicDataSource;
    }
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        */
/**
         * 配置数据源，没有则使用dynamicDataSource
         *//*

        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
}
*/
