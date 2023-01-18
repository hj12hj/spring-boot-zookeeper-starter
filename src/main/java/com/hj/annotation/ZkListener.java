package com.hj.annotation;


import com.hj.enums.ListenerModel;

import java.lang.annotation.*;

/**
 * Zk 监听器
 *
 * @author hejie
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZkListener {

    String path();


    ListenerModel type() default ListenerModel.NODE;

}
