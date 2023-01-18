package com.hj.aop;

import com.hj.annotation.ZkLocker;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

/**
 * @author: hj
 * @date: 2023/1/18
 * @time: 8:56 AM
 */
@Aspect
@Order(-50)
public class LockAop {


    @Pointcut("@annotation(zkLocker)")
    public void lockAspect(ZkLocker zkLocker) {
    }

    @Autowired
    private CuratorFramework zkClient;


    @Around("lockAspect(zkLocker)")
    public Object around(ProceedingJoinPoint joinPoint, ZkLocker zkLocker) throws Throwable {

        InterProcessMutex lock = new InterProcessMutex(zkClient, zkLocker.path());
        try {
            lock.acquire();
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new RuntimeException("获取锁失败");
        } finally {
            lock.release();
        }

    }

}
