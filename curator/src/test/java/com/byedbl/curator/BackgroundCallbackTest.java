package com.byedbl.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
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
}
