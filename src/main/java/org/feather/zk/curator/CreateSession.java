package org.feather.zk.curator;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @projectName: zk-api
 * @package: org.feather.zk.curator
 * @className: CreateSession
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/8 16:51
 * @version: 1.0
 */
public class CreateSession {
    public static void main(String[] args) throws Exception {
        //不使用fluent 编程风格
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", exponentialBackoffRetry);
        curatorFramework.start();
        System.out.println("会话创建 了");


        //使用fluent 编程风格

        CuratorFramework client = CuratorFrameworkFactory.builder().
                 connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(30000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .namespace("base")
                .build();
        client.start();



    }
}
