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
public class DeleteNode {
    public static void main(String[] args) {
        /*
        创建一个zkClient实例就可以完成连接，完成会话的创建
        zkClient通过对zookeeperApi的内部封装，将这个异步创建会话同步化了
         */
        ZkClient zkClient = new ZkClient("localhost:2181");

        //递归删除节点
        String path="/feather-zkclient/c1";
        zkClient.createPersistent(path+"/c11");
        zkClient.deleteRecursive(path);
        System.out.println("递归删除成功");


    }
}
