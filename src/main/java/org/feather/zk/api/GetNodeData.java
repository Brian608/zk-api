package org.feather.zk.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @projectName: zk-api
 * @package: orf.feather.zk
 * @className: CreateNode
 * @author: feather(杜雪松)
 * @description:创建节点
 * @since: 2022/9/8 08:30
 * @version: 1.0
 */
public class GetNodeData implements Watcher {

    private  static final CountDownLatch countDownLatch=new CountDownLatch(1);

    private  static  ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
         * 客户端可以通过创建一个zk实例来链接zk服务器
         * new Zookeeper （connectionString ，sessionTimeOut，Watcher）
         * connectionString 链接地址，IP：端口
         * sessionTimeOut 会话超时时间 单位毫秒
         * Watcher：监听器 （当特定事件触发监听器时，zk会通过Watcher通知到客户端）
         */
         zooKeeper = new ZooKeeper("localhost:2181", 5000, new GetNodeData());
        System.out.println(zooKeeper.getState());
        //计数工具类  countDownLatch 不让main方法结束，让线程处于等待阻塞
       // countDownLatch.await();
        Thread.sleep(Integer.MAX_VALUE);


    }


    /**
     * 回调方法 ：处理来自服务器端的Watcher通知
     * @param watchedEvent
     */

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getType()==Event.EventType.NodeChildrenChanged){
            List<String> children =null;
            try {
                children= zooKeeper.getChildren("/feather-persistent", true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(children);
        }
        //SyncConnected
        if (watchedEvent.getState()== Event.KeeperState.SyncConnected){
            //解除主程序countDownLatch  的等待阻塞
            System.out.println("process方法执行了...");
            //获取节点数据
            try {
                getNodeData();
                getChildrens();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取某个节点的内容
     */
    private void getNodeData() throws InterruptedException, KeeperException {
        /*
         *path 获取数据的路径
         * watch 是否开启监听
         * null 表示获取最新版本的数据
         * zk.getData（path,watch,stat）
         */
        byte[] data = zooKeeper.getData("/feather-persistent", false, null);
        System.out.println(new String(data));

    }
    /**
     * 获取某个节点的子节点列表的方法
     */
    public static  void  getChildrens() throws InterruptedException, KeeperException {
        /*
        path :路径
        watch：是否启动监听，当子节点列表发生变化，会触发监听
        zookeeper.getChildren(path,watch)
         */
        List<String> children = zooKeeper.getChildren("/feather-persistent", true);
        System.out.println(children);
    }
}
