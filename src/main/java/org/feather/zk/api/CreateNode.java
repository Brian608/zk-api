package org.feather.zk.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
public class CreateNode implements Watcher {

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
         zooKeeper = new ZooKeeper("localhost:2181", 5000, new CreateNode());
        System.out.println(zooKeeper.getState());
        //计数工具类  countDownLatch 不让main方法结束，让线程处于等待阻塞
       // countDownLatch.await();
        Thread.sleep(Integer.MAX_VALUE);


    }

    private static void createNodeSync() throws InterruptedException, KeeperException {
        //持久节点
        String  node_persistent = zooKeeper.create("/feather-persistent", "持久节点内容".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 临时节点
        String  node_ephemeral = zooKeeper.create("/feather-ephemeral", "临时节点内容".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        //持久顺序节点
        String  node_persistent_sequential = zooKeeper.create("/feather-persistent_sequential", "持久顺序节点内容".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("持久节点内容："+node_persistent);
        System.out.println("临时节点："+node_ephemeral);
        System.out.println("持久顺序节点:"+node_persistent_sequential);
    }


    /**
     * 回调方法 ：处理来自服务器端的Watcher通知
     * @param watchedEvent
     */

    @Override
    public void process(WatchedEvent watchedEvent) {
        //SyncConnected
        if (watchedEvent.getState()== Event.KeeperState.SyncConnected){
            //解除主程序countDownLatch  的等待阻塞
            System.out.println("process方法执行了...");
           // countDownLatch.countDown();

            //创建节点
            try {
                createNodeSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }
}
