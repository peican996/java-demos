package test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @author wangpeican
 * @date 2024/2/17 22:23
 */
public class PathChildrenCacheListenerIml implements PathChildrenCacheListener {

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) {
        // 获取事件类型
        PathChildrenCacheEvent.Type eventType = event.getType();
        // 获取节点路径
        String nodePath = event.getData().getPath();
        switch (eventType) {
            case CHILD_ADDED:
                System.out.println("Node added: " + nodePath);
                break;
            case CHILD_UPDATED:
                System.out.println("Node updated: " + nodePath);
                break;
            case CHILD_REMOVED:
                System.out.println("Node removed: " + nodePath);
                break;
            default:
                // 其他事件类型
                break;
        }
    }
}
