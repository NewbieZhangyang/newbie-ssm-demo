package com.newbie.util.dbMultipleRouting;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DynamicDataSource由自己实现，实现AbstractRoutingDataSource，数据源由自己指定。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 重写父类的方法
     * 利用反射机制，从父类中拿到数据源的集合resolvedDataSources，以此设置主库的节点（设置 DynamicDataSourceHolder.dataSouceKeyList）
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        //由于父类的resolvedDataSources属性是私有的子类获取不到，需要使用反射获取
        Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
        field.setAccessible(true);
        Map<String, DataSource> resolvedDataSources = null;
        try {
            resolvedDataSources = (Map<String, DataSource>) field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //根据数据源集合resolvedDataSources，来处理主库的集合
        if (resolvedDataSources != null) {
            Set<Map.Entry<String, DataSource>> entrySet = resolvedDataSources.entrySet();
            Iterator<Map.Entry<String, DataSource>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                //判断key是否以master开头，如果是则认为是主库，并将该key值保存到：ynamicDataSourceHolder.dataSouceKeyList
                String key = iterator.next().getKey();
                if (key != null && key.startsWith("master")) {
                    //将主库的key，加入到主库集合列表中
                    DynamicDataSourceHolder.addDataSourceKey(key);
                    //根据主库集合列表，生成一致性hash算法节点
                    DynamicDataSourceHolder.initConsistentHash();
                }
            }
        }
    }

    /**
     * 动态设置数据源标识
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSourceHolder.getDataSourceFinalKey();
    }

}
