package test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

/**
 * @author wangpeican
 * @date 2024/2/17 23:11
 */
public class ClientUtil {

    private static final String ZOOKEEPER_CONNECTION_STRING = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT_MS = 5000; // 会话超时时间
    private static final int CONNECTION_TIMEOUT_MS = 3000; // 连接超时时间

    public static CuratorFramework getClient() {
        // 使用 CuratorFrameworkFactory 创建 CuratorFramework 实例
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONNECTION_STRING, SESSION_TIMEOUT_MS, CONNECTION_TIMEOUT_MS, new ExponentialBackoffRetry(1000, 3) // 设置重试策略
        );
        // 启动 CuratorFramework 实例
        curatorFramework.start();
        return curatorFramework;
    }

    public static void createPath(CuratorFramework curatorFramework,CreateMode createMode,  String path, String data) {
        // 创建临时节点来表示服务
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(createMode)
                    .forPath(path, data.getBytes());
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public static List<String> getChildren (CuratorFramework curatorFramework, String parentNodePath) throws Exception {
        return curatorFramework.getChildren().forPath(parentNodePath);
    }

    public static String getPathData(CuratorFramework curatorFramework,String path) throws Exception {
        byte[] data = curatorFramework.getData().forPath(path);
        return new String(data);
    }

}
