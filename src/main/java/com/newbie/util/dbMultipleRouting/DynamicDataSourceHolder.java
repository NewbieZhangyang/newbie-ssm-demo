package com.newbie.util.dbMultipleRouting;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义类：用来保存数据源标识，从而在实例化DynamicDataSource时来指定要使用的数据源实例
 */
public class DynamicDataSourceHolder {

    /**
     * 注意：数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
     */
    private static final ThreadLocal<String> DATA_SOURCE_KEY = new ThreadLocal<String>();
    /**
     * 随机生成一个基数，用于计算从库索引值
     */
    private static final ThreadLocal<AtomicInteger> BASE_NUMBER = new ThreadLocal<AtomicInteger>();

    public static String getDataSourceKey(){
        return DATA_SOURCE_KEY.get();
    }

    public static void setDataSourceKey(String dataSourceKey){
        DATA_SOURCE_KEY.set(dataSourceKey);
    }

    public static void clearDataSourceKey(){
        DATA_SOURCE_KEY.remove();
    }

    public static Integer getBaseNumber() {
        return BASE_NUMBER.get().get();
    }
    public static void setBaseNumber(int baseNumber){
        AtomicInteger atomicInteger = new AtomicInteger(baseNumber);
        BASE_NUMBER.set(atomicInteger);
    }
    public static void clearBaseNumber(){
        BASE_NUMBER.remove();
    }
}
