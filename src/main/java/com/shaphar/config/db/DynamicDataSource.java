package com.shaphar.config.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setDatasource(String dataSource){
        threadLocal.set(dataSource);
    }

    public static void setDatasource(String dataSource, JdbcTemplate jdbcTemplate, DataSource dataSources){
        threadLocal.set(dataSource);
        jdbcTemplate.setDataSource(dataSources);
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return threadLocal.get();
    }
}
