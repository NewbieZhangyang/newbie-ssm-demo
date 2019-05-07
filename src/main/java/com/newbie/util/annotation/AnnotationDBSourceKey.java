package com.newbie.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类型：使用注解来标识数据源
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationDBSourceKey {
    /**
     * value的值：来标识数据源
     * @return
     */
    String value();
}
