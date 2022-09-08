package org.feather.zk.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
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
public class NodeApi {
    public static void main(String[] args) throws InterruptedException {
        /*
        创建一个zkClient实例就可以完成连接，完成会话的创建
        zkClient通过对zookeeperApi的内部封装，将这个异步创建会话同步化了
         */
        ZkClient zkClient = new ZkClient("localhost:2181");
        String path="/feather-zkclient-Ep";
        boolean exists = zkClient.exists(path);
        if (!exists){
            //创建临时节点
            zkClient.createEphemeral(path,"123");

        }
        //读取节点内容
        Object readData = zkClient.readData(path);
        System.out.println(readData);

        //注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            /**
             * 当节点数据内容发生变化时，执行的回调方法
             * @param s  path
             * @param o 变化后的节点内容
             * @throws Exception
             */
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s+"该节点内容被更新，更新的内容为:"+o);
            }

            /**
             * 当前节点被删除时，会执行的回调方法
             * @param s
             * @throws Exception
             */
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s+"该节点被删除");
            }
        });
        //更新节点内容
        zkClient.writeData(path,"456");
        Thread.sleep(1000);

        //删除节点
        zkClient.delete(path);
        Thread.sleep(1000);

    }
}
