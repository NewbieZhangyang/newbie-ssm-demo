package com.newbie.util.dbReadWriteSeparation;


import com.newbie.util.dbMultipleRouting.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  定义数据源的AOP切面，通过该Service的方法名判断是应该走读库还是写库
 */
@Component
@Aspect
public class DataSourceAspect {

    /**
     * 定义切面植入的增强功能：在service对象的方法执行前，进行切入
     * @param joinpoint ： 切面对象
     */
    @Before("execution(* *..service.*.*(..))")
    public void before(JoinPoint joinpoint){
        String methodName = joinpoint.getSignature().getName();
        //根据方法名称的前缀，判断是否是查询方法
        //如果是查询方法，则使用从库；否则，使用主库
        if(isSlaveMethod(methodName)){
            //标记为从库
            DynamicDataSourceHolder.setDataSourceKey("slave1");
        }else {
            //标记为主库
            DynamicDataSourceHolder.setDataSourceKey("master");
        }
        System.out.println("========= 切入的方法名称："+methodName+"，重置当前线程数据源：key = "+DynamicDataSourceHolder.getDataSourceKey());

    }

    /**
     * 根据方法名称的前缀，判断是否是查询方法
     * @param methodName : 方法名称
     * @return true ：是查询方法<br/>
     *          false : 不是查询方法
     */
    public boolean isSlaveMethod(String methodName){
        if(methodName == null){
            return false;
        }
        //遍历方式，判断方法的前缀
        List<String> methodPrefix = new ArrayList<String>();
        methodPrefix.add("query");
        methodPrefix.add("search");
        methodPrefix.add("find");
        methodPrefix.add("get");
        Iterator<String> iterator = methodPrefix.iterator();
        while (iterator.hasNext()){
            String prefixName = iterator.next();
            if(methodName.startsWith(prefixName)){
                return true;
            }
        }
        return false;
    }
}
