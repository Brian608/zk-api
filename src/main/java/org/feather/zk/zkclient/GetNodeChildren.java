package org.feather.zk.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @projectName: zk-api
 * @package: org.feather.zk.zkclient
 * @className: CreateSession
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/8 15:26
 * @version: 1.0
 */
public class GetNodeChildren {
    public static void main(String[] args) throws InterruptedException {
        /*
        创建一个zkClient实例就可以完成连接，完成会话的创建
        zkClient通过对zookeeperApi的内部封装，将这个异步创建会话同步化了
         */
        ZkClient zkClient = new ZkClient("localhost:2181");

        List<String> children = zkClient.getChildren("/feather-zkclient");
        System.out.println(children);


        //注册监听事件
        /**
         * 客户端可以对一个不存在的节点进行子节点变更的监听
         * 只要该节点的子节点列表发生变化，或者该节点本身被创建或者删除，都会触发监听
         */
        zkClient.subscribeChildChanges("/feather-zkclient-get", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                /**
                 * s :parentPath
                 * list: 变化后子节点列表
                 */
                System.out.println(parentPath+"的节点列表发生了变化，变化后的子节点列表为:"+list);
            }
        });
        //测试
        zkClient.createPersistent("/feather-zkclient-get");
        Thread.sleep(1000);


        zkClient.createPersistent("/feather-zkclient-get/c1");


    }
}
