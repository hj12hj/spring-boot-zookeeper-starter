package com.hj.annotation;


import java.lang.annotation.*;

/**
 * Zk 分布式锁
 *
 * @author hejie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZkLocker {

    String path();

    String value() default "";

}
