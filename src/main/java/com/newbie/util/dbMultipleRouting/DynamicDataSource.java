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
     * 从库总数，初始为-1，会调用自增方法进行赋值
     * 用于计算从库的索引值 = 基数randomNumber % 从库总数slaveCount
     */
    private AtomicInteger slaveCount = new AtomicInteger(0);
    /**
     * 从库对应的key值集合
     */
    private List<String> stringList = new ArrayList<String>(0);


    /**
     * 重写父类的方法
     * 利用反射机制，从父类中拿到所有数据源的集合resolvedDataSources，以此来计算从库的总数、设置从库对应的key值列表
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
        //根据数据源集合resolvedDataSources，来计算从库的总数、设置从库对应的key值列表
        if (resolvedDataSources != null) {
            Set<Map.Entry<String, DataSource>> entrySet = resolvedDataSources.entrySet();
            Iterator<Map.Entry<String, DataSource>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                //判断key是否以slave开头，如果是则认为是从库，否则认为是主库
                String key = iterator.next().getKey();
                if (key != null && key.startsWith("slave")) {
                    int index = slaveCount.incrementAndGet();   // 从0开始赋值
                    //slaveMap.put(index,key);
                    stringList.add(key);
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
        String originalKey = DynamicDataSourceHolder.getDataSourceKey();
        //判断如果是主库的话，直接返回结果
        if (originalKey == null || originalKey.startsWith("master")) {
            System.out.println("======== 当前要操作的是 ：主库， key = "+originalKey);
            DynamicDataSourceHolder.setDataSourceKey(originalKey);  //并没有实际意义，只是为了在service方法中拿取数据，进行前台显示
            return originalKey;
        }
        //如果是从库的化，计算从库的索引，来确定要查询的从库
        String slaveKey = getSlaveIndex();
        DynamicDataSourceHolder.setDataSourceKey(slaveKey);
        System.out.println("～～～～～～～ 当前要操作的是 ：从库，slaveKey = "+slaveKey+", originalKey = "+originalKey);
        return slaveKey;
    }

    /**
     * 设置要查询的从库key
     * 第一步：计算从库的索引值 = 基数randomNumber % 从库总数slaveCount
     * 第二步：从stringList中，通过索引值获得key
     *
     * @return key
     */
    public String getSlaveIndex() {
        //随机生成一个基数：randomNumber
        int randomNumber = DynamicDataSourceHolder.getBaseNumber();
        int slaveCountNumber = slaveCount.get();
        int index = randomNumber % slaveCountNumber;
        String slaveKey = stringList.get(index);
        System.out.println("@@@@@@@@@@@@@@@@ getSlaveIndex(),index = "+index+", randomNumber = "+randomNumber+", slaveCountNumber = "+slaveCountNumber);
        System.out.println("@@@@@@@@@@@@@@@@ getSlaveIndex(),slaveKey = "+slaveKey);
        return slaveKey;
    }
}
