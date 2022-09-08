package org.feather.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;

/**
 * @projectName: zk-api
 * @package: org.feather.zk.curator
 * @className: CreateSession
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/8 16:51
 * @version: 1.0
 */
public class CreateNode {
    public static void main(String[] args) throws Exception {

        //使用fluent 编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder().
                 connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(30000)
               .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .namespace("base")
                .build();
        client.start();

        String path="/feather-curator/c1";
        String forPath = client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes(StandardCharsets.UTF_8));
        System.out.println("节点递归创建成功，该节点路径:"+forPath);


    }
}
