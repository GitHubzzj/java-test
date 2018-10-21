package com.byedbl.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * https://www.jianshu.com/p/70151fc0ef5d
 */
public class ClientTest {

    private static String connectionInfo = "eshop-cache01-64:2181";

    private static CuratorFramework client;

    @BeforeClass
    public static void initClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionInfo)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .namespace("base")
                        .build();
        client.start();
    }

    @Test
    public void Builder() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.newClient(
                        connectionInfo,
                        5000,
                        3000,
                        retryPolicy);
        client.start();
    }

    @Test
    public void testFluentBuilder() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionInfo)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();

        client.start();

    }


    /**
     * 业务隔离的空间 base
     */
    @Test
    public void testNamespaceBuilder() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionInfo)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .namespace("base")
                        .build();
        client.start();
    }


    /**
     * 如果没有设置节点属性，节点创建模式默认为持久化节点，内容默认为空
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        client.create().forPath("/path");
    }

    @Test
    public void testCreateWithContext() throws Exception {
        client.create().forPath("/path1","init".getBytes());
    }


    /**
     * @throws Exception
     * 创建一个节点，指定创建模式（临时节点），内容为空
     */
    @Test
    public void testCreateWithMode() throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/pathmode");
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/pathmode1","init".getBytes());
    }

    @Test
    public void testCreateParentIfNeeded() throws Exception {
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/path3/test","init".getBytes());
    }

    /**
     *  只能删除叶子节点,否则会抛异常
     *  org.apache.zookeeper.KeeperException$NotEmptyException: KeeperErrorCode = Directory not empty for /base/path
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        client.create().creatingParentContainersIfNeeded().forPath("/path/for/delete");
        client.delete().forPath("/path");
    }

    /**
     * 会删除叶子节点
     * @throws Exception
     */
    @Test
    public void testDeleteChildren() throws Exception {
        //client.create().creatingParentContainersIfNeeded().forPath("/path/for/delete");
        client.delete().deletingChildrenIfNeeded().forPath("/path");
    }

    @Test
    public void testDeleteVersion() throws Exception {
//        client.create().creatingParentContainersIfNeeded().forPath("/path/for/delete");
        client.delete().deletingChildrenIfNeeded().withVersion(1).forPath("/path");
    }


    @Test
    public void testDeleteGuaranteed() throws Exception {
        client.create().creatingParentContainersIfNeeded().forPath("/path/for/delete");
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/path");
    }

    @Test
    public void testGetData() throws Exception {
        String path= "/path/get/data";
        client.delete().deletingChildrenIfNeeded().forPath(path);
        client.create().creatingParentContainersIfNeeded().forPath(path, "getData".getBytes());
        byte[] bytes = client.getData().forPath(path);
        System.out.println(new String(bytes));
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    @Test
    public void testGetDataWithStat() throws Exception {
        String path= "/path/get/data";
        client.create().creatingParentContainersIfNeeded().forPath(path, "testGetDataWithStat".getBytes());
        Stat stat = new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));
        System.out.println(stat);
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    @Test
    public void testSetData() throws Exception {
        String path = "/path/set/data";
        client.create().creatingParentContainersIfNeeded().forPath(path, "1".getBytes());
        Stat stat = client.setData().forPath(path, "data".getBytes());
        System.out.println(new String(client.getData().forPath(path)));
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    @Test
    public void testSetDataWithVersion() throws Exception {
        String path = "/path/set/data/version";
        client.delete().deletingChildrenIfNeeded().forPath(path);
        client.create().creatingParentContainersIfNeeded().forPath(path, "1".getBytes());
        for(int i=0;i<10;i++) {

            Stat stat = client.setData().forPath(path, ("data"+i).getBytes());

        }
        System.out.println(new String(client.getData().forPath(path)));
        //org.apache.zookeeper.KeeperException$BadVersionException: KeeperErrorCode = BadVersion for /base/path/set/data/version
        Stat stat = client.setData().withVersion(10).forPath(path, "2".getBytes());
        System.out.println(new String(client.getData().forPath(path)));
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    @Test
    public void testExists() throws Exception {
        String path = "/path/exists";
        Stat stat = client.checkExists().forPath(path);
        System.out.println(stat); //null
        String create = client.create().creatingParentContainersIfNeeded().forPath(path, (path+"value1").getBytes());
        //path/exists
        System.out.println(create);
        Stat stat1 = client.checkExists().forPath(path);
        System.out.println(stat1);
        client.delete().deletingChildrenIfNeeded().forPath(path);

    }


    @Test
    public void testGetChildren() throws Exception {
        client.create().creatingParentsIfNeeded().forPath("/path/child1");
        client.create().creatingParentsIfNeeded().forPath("/path/child2");
        client.create().creatingParentsIfNeeded().forPath("/path/child3/child33");
        String path = "/path";
        List<String> strings = client.getChildren().forPath(path);
        System.out.println(strings);
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    @Test
    public void testTransaction() throws Exception {
        String path = "/path";
//        client.delete().deletingChildrenIfNeeded().forPath(path);
        client.inTransaction()
//                .check().forPath(path).and()
                .create().withMode(CreateMode.PERSISTENT).forPath(path,"data".getBytes())
                .and()
                .setData().forPath(path,"data2".getBytes())
                .and()
                .commit();

        System.out.println(new String(client.getData().forPath(path)));
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

}
