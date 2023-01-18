package com.hj.config;

import com.hj.core.ZookeeperTemplate;
import com.hj.processor.ZkListenerProcessor;
import com.hj.properties.ZkProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author: hj
 * @date: 2023/1/17
 * @time: 2:13 PM
 */

@EnableConfigurationProperties(value = ZkProperties.class)
@ConditionalOnClass(ZkProperties.class)
public class ZkConfig {

    @Autowired
    private ZkProperties zkProperties;

    @Bean
    public CuratorFramework zkClient() {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder().connectString(zkProperties.getConnectString())
                .sessionTimeoutMs(zkProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(zkProperties.getConnectionTimeoutMs())
                .retryPolicy(new ExponentialBackoffRetry(zkProperties.getBaseSleepTimeMs(), zkProperties.getMaxRetries()))
                .build();
        zkClient.start();
        return zkClient;
    }


    @Bean
    public ZkListenerProcessor zkListenerProcessor() {
        return new ZkListenerProcessor();
    }


    @Bean
    public ZookeeperTemplate zookeeperTemplate() {
        return new ZookeeperTemplate();
    }

}
