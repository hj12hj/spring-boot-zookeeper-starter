package com.hj.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作类
 *
 * @author: hj
 * @date: 2023/1/17
 * @time: 1:48 PM
 */
public class ZookeeperTemplate {

    @Autowired
    private CuratorFramework zkClient;


    /**
     * 创建节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void createPath(String path, String data) throws Exception {
        zkClient.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
    }


    /**
     * 删除节点
     *
     * @param path
     * @throws Exception
     */
    public void deletePath(String path) throws Exception {
        zkClient.delete().deletingChildrenIfNeeded().forPath(path);
    }


    /**
     * 更新节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void updatePath(String path, String data) throws Exception {
        zkClient.setData().forPath(path, data.getBytes());
    }

    /**
     * 获取节点数据
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String getData(String path) throws Exception {
        return new String(zkClient.getData().forPath(path));
    }


    /**
     * 判断节点是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isExist(String path) throws Exception {
        return zkClient.checkExists().forPath(path) != null;
    }


    /**
     * 获取子节点
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String getChildren(String path) throws Exception {
        return zkClient.getChildren().forPath(path).toString();
    }


    /**
     * 创建临时节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void createEphemeralPath(String path, String data) throws Exception {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes());
    }


    /**
     * 创建临时顺序节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void createEphemeralSequentialPath(String path, String data) throws Exception {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data.getBytes());
    }


    /**
     * 创建永久顺序节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void createPersistentSequentialPath(String path, String data) throws Exception {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path, data.getBytes());
    }


    /**
     * 创建永久节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void createPersistentPath(String path, String data) throws Exception {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
    }

    /**
     * 获取节点状态
     *
     * @param path
     * @return
     * @throws Exception
     */
    public Stat getStat(String path) throws Exception {
        return zkClient.checkExists().forPath(path);
    }


    /**
     * 监听tree节点
     *
     * @param treeCache
     * @throws Exception
     */
    public void addTreeListener(TreeCache treeCache, TreeCacheListener treeCacheListener) throws Exception {
        treeCache.start();
        treeCache.getListenable().addListener(treeCacheListener);
    }


    /**
     * 监听Node节点
     *
     * @param nodeCache
     * @param nodeCacheListener
     * @throws Exception
     */
    public void addNodeListener(NodeCache nodeCache, NodeCacheListener nodeCacheListener) throws Exception {
        nodeCache.start(true);
        nodeCache.getListenable().addListener(nodeCacheListener);
    }


    /**
     * 监听path节点
     *
     * @param pathChildrenCache
     * @param pathChildrenCacheListener
     * @throws Exception
     */
    public void addPathListener(PathChildrenCache pathChildrenCache, PathChildrenCacheListener pathChildrenCacheListener) throws Exception {
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
    }

}
