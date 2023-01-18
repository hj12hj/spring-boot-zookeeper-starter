package com.hj.annotation;


import com.hj.config.ZkConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Zk 监听器
 *
 * @author hejie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ZkConfig.class)
public @interface EnableZk {


}
