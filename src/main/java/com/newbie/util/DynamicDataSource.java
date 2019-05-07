package com.newbie.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * DynamicDataSource由自己实现，实现AbstractRoutingDataSource，数据源由自己指定。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        // 从自定义的位置获取数据源标识
        return DynamicDataSourceHolder.getDataSourceKey();
    }
}
