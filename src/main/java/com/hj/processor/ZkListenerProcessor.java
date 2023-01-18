package com.hj.processor;

import com.hj.annotation.ZkListener;
import com.hj.core.ZookeeperTemplate;
import com.hj.enums.ListenerModel;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * @author: hj
 * @date: 2023/1/17
 * @time: 3:49 PM
 */
public class ZkListenerProcessor implements BeanPostProcessor {

    @Autowired
    private ZookeeperTemplate zookeeperTemplate;

    @Autowired
    private CuratorFramework zkClient;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            // 扫描注册
            ZkListener zkListener = method.getAnnotation(ZkListener.class);
            if (zkListener != null) {
                String path = zkListener.path();
                ListenerModel type = zkListener.type();
                switch (type) {
                    case PATH:
                        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, path, true);
                        try {
                            zookeeperTemplate.addPathListener(pathChildrenCache, (curatorFramework, event) -> {
                                try {
                                    Object[] args = new Object[]{curatorFramework, event};
                                    method.invoke(bean, args);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case NODE:
                        NodeCache nodeCache = new NodeCache(zkClient, path);
                        try {
                            zookeeperTemplate.addNodeListener(nodeCache, () -> {
                                try {
                                    Object[] args = new Object[]{nodeCache};
                                    method.invoke(bean,args);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        break;
                    case TREE:
                        TreeCache treeCache = new TreeCache(zkClient, path);
                        try {
                            zookeeperTemplate.addTreeListener(treeCache, (curatorFramework, event) -> {
                                try {
                                    Object[] args = new Object[]{curatorFramework, event};
                                    method.invoke(bean, args);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        break;
                }


            }
        });


        return bean;
    }
}
