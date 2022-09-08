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
public class DeleteNode {
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

      //删除节点
        String path="/feather-curator";
        client.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);
        System.out.println("删除成功，删除的节点:"+path);


    }
}
