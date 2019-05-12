package com.newbie.util.dbMultipleRouting;

import com.newbie.util.consistentHash.ConsistentHash;
import com.newbie.util.consistentHash.HashFunction;

import java.util.ArrayList;
import java.util.List;
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
     * 分库方案中，用于计算hash码的基值
     */
    private static final ThreadLocal<String> CONSISTENT_HASH_KEY = new ThreadLocal<String>();
    /**
     * 所有主库对应的key值集合
     * 在一致性hash算法中，用于生成主机虚拟节点
     */
    private static List<String> dataSouceKeyList = new ArrayList<String>();

    /**
     * 一致性hash算法工具
     */
    private static ConsistentHash<String> consistentHashDB = null;


    public static String getDataSourceKey(){
        return DATA_SOURCE_KEY.get();
    }

    public static void setDataSourceKey(String dataSourceKey){
        DATA_SOURCE_KEY.set(dataSourceKey);
    }

    public static void clearDataSourceKey(){
        DATA_SOURCE_KEY.remove();
    }

    public static String geConsistentHashKey(){
        return CONSISTENT_HASH_KEY.get();
    }

    public static void setConsistentHashKey(String consistentHashKey){
        CONSISTENT_HASH_KEY.set(consistentHashKey);
    }

    public static void clearConsistentHashKey(){
        CONSISTENT_HASH_KEY.remove();
    }


    public static List<String> getDataSouceKeyList() {
        return dataSouceKeyList;
    }

    public static void setDataSouceKeyList(List<String> dataSouceKeyList) {
        DynamicDataSourceHolder.dataSouceKeyList = dataSouceKeyList;
    }

    /**
     * 向主库集合中，增加一个主库节点
     * @param key
     */
    public static void addDataSourceKey(String key){
        dataSouceKeyList.add(key);
    }
    /**
     * 使用主库集合，实现一致性hash算法
     */
    public static void initConsistentHash(){
        HashFunction hashFunction = new HashFunction();
        consistentHashDB = new ConsistentHash<String>(hashFunction,100,dataSouceKeyList);
    }

    /**
     * 按照一致性hash分表方案，计算真实存储的数据源节点，返回对应的数据源的key
     * @return
     */
    public static String getDataSourceFinalKey(){
        //计算真实节点
        String originalKey = consistentHashDB.get(CONSISTENT_HASH_KEY.get());
        //判断要操作的是主库，还是从库
        if(DATA_SOURCE_KEY.get() == null || DATA_SOURCE_KEY.get().startsWith("master")){
            return originalKey;
        }else{
            return originalKey.replace("master","slave");

        }

    }

}
