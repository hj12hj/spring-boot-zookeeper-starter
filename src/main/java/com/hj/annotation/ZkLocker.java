package com.hj.annotation;


import com.hj.enums.ListenerModel;

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


}
