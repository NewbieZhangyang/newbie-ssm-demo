package com.newbie.util.dbMultipleRouting.annotation;

import com.newbie.util.dbMultipleRouting.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 定义切面类：在执行DAO接口的方法前，获取方法的@AnnotationDBSourceKey注解，根据注解的值来动态设置数据源
 */
@Component
@Aspect
public class DynamicDBSourceAspect {
    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    @Before("execution(* com.newbie.service.*.*(..))")
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        System.out.println("intercept()----,target:"+target+",signature:"+signature+",signature.getMethod():"+signature.getMethod());

        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(AnnotationDBSourceKey.class)) {
                AnnotationDBSourceKey source = clazz.getAnnotation(AnnotationDBSourceKey.class);
                DynamicDataSourceHolder.setDataSourceKey(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(AnnotationDBSourceKey.class)) {
                AnnotationDBSourceKey source = m.getAnnotation(AnnotationDBSourceKey.class);
                DynamicDataSourceHolder.setDataSourceKey(source.value());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }
}
