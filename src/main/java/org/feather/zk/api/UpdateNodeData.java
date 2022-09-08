package org.feather.zk.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

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
public class UpdateNodeData implements Watcher {

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
         zooKeeper = new ZooKeeper("localhost:2181", 5000, new UpdateNodeData());
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
        //SyncConnected
        if (watchedEvent.getState()== Event.KeeperState.SyncConnected){
            //解除主程序countDownLatch  的等待阻塞
            System.out.println("process方法执行了...");
            //更新数据节点内容
            try {
                updateNodeSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 更新数据节点内容
     */
    private void updateNodeSync() throws InterruptedException, KeeperException {
        /*
        path 路径
        data：要修改的内容byte[]
        version :为-1 ，表示当前最新版本的数据进行修改
        zookeeper.setData(path,data,version)
         */
        byte[] data = zooKeeper.getData("/feather-persistent", false, null);
        System.out.println("修改前的值:"+new String(data));

        //修改节点数据
        Stat stat = zooKeeper.setData("/feather-persistent", "客户端修改了节点数据".getBytes(StandardCharsets.UTF_8), -1);

        byte[] data2= zooKeeper.getData("/feather-persistent", false, stat);
        System.out.println("修改后的值:"+new String(data2));

    }
}
