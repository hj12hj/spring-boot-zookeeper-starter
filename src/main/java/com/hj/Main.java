package org.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

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




        Stat stat = new Stat();
        zkClient.getData().storingStatIn(stat).forPath("/zk-test0000000000");
        System.out.println(stat.getVersion());


    }
}