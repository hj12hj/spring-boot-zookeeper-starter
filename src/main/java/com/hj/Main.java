package com.hj;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * @author: hj
 * @date: ${DATE}
 * @time: ${TIME}
 */
public class Main {
    public static void main(String[] args) throws Exception {

        CuratorFramework zkClient = CuratorFrameworkFactory.builder().connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .build();

        zkClient.start();


//        zkClient.delete().deletingChildrenIfNeeded().forPath("/test");
//
//        zkClient.create().forPath("/test", "123".getBytes());


        InterProcessMutex lock = new InterProcessMutex(zkClient, "/test");

        lock.acquire(1000, TimeUnit.SECONDS);


        lock.release();


    }
}