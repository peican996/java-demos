package test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author wangpeican
 * @date 2024/2/17 20:49
 */
public class CuratorTest {

    private static final String PATH = "/services";
    private static final String SERVICE_PATH = "/services/my-service";

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = ClientUtil.getClient();
        ClientUtil.createPath(curatorFramework, CreateMode.PERSISTENT, SERVICE_PATH, "test");
        // 创建临时节点来表示服务
        ClientUtil.createPath(curatorFramework, CreateMode.EPHEMERAL_SEQUENTIAL, SERVICE_PATH, "test_data");
        System.out.println("Service registered successfully: " + SERVICE_PATH);
        List<String> list = ClientUtil.getChildren(curatorFramework, "/services");
        for (String str : list) {
            System.out.println(str);
        }
        System.out.println(ClientUtil.getPathData(curatorFramework, SERVICE_PATH));
        try {
            PathChildrenListen.pathChildrenListen(curatorFramework, PATH, new PathChildrenCacheListenerIml());
            Thread.sleep(1000000);
        } catch (Exception e) {
            // 处理连接异常
            e.printStackTrace();
        } finally {
            // 关闭连接
            curatorFramework.close();
        }
    }
}
