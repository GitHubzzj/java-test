package com.byedbl.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class BackgroundCallbackTest {

    private static String connectionInfo = "eshop-cache01-64:2181";

    private static CuratorFramework client;

    @BeforeClass
    public static void initClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionInfo)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .namespace("background")
                        .build();
        client.start();
    }

    /**
     *
     响应码	意义
     0	OK，即调用成功
     -4	ConnectionLoss，即客户端与服务端断开连接
     -110	NodeExists，即节点已经存在
     -112	SessionExpired，即会话过期
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        Executor executor = Executors.newFixedThreadPool(2);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> {      System.out.println(String.format("eventType:%s,resultCode:%s",curatorEvent.getType(),curatorEvent.getResultCode()));
                },executor)
                .forPath("path");

    }

    @Test
    public void testPathCache() throws Exception {
        String path = "/example";
//        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionInfo, new ExponentialBackoffRetry(1000, 3));
//        client.start();
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start();
        PathChildrenCacheListener cacheListener = (client1, event) -> {
            System.out.println("事件类型：" + event.getType());
            if (null != event.getData()) {
                System.out.println("节点数据：" + event.getData().getPath() + " = " + new String(event.getData().getData()));
            }
        };
        cache.getListenable().addListener(cacheListener);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test01", "01".getBytes());
        Thread.sleep(10);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test02", "02".getBytes());
        Thread.sleep(10);
        client.setData().forPath("/example/pathCache/test01", "01_V2".getBytes());
        Thread.sleep(10);
        for (ChildData data : cache.getCurrentData()) {
            System.out.println("getCurrentData:" + data.getPath() + " = " + new String(data.getData()));
        }
        client.delete().forPath("/example/pathCache/test01");
        Thread.sleep(10);
        client.delete().forPath("/example/pathCache/test02");
        Thread.sleep(1000 * 5);
        cache.close();
        client.close();
        System.out.println("OK!");

    }
}
