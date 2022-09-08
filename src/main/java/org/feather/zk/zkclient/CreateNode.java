package org.feather.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @projectName: zk-api
 * @package: org.feather.zk.zkclient
 * @className: CreateSession
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/8 15:26
 * @version: 1.0
 */
public class CreateNode {
    public static void main(String[] args) {
        /*
        创建一个zkClient实例就可以完成连接，完成会话的创建
        zkClient通过对zookeeperApi的内部封装，将这个异步创建会话同步化了
         */
        ZkClient zkClient = new ZkClient("localhost:2181");

        /**
         * createParents  是否要创建父节点，如果值为true，那么就会递归创建节点
         */
        zkClient.createPersistent("/feather-zkclient/c1",true);

        

    }
}
