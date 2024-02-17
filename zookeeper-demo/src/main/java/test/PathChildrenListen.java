package test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @author wangpeican
 * @date 2024/2/17 23:24
 */
public class PathChildrenListen {
    public static void pathChildrenListen(CuratorFramework curatorFramework, String path, PathChildrenCacheListener pathChildrenCacheListener) {
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, path).build();
        CuratorCacheListener listener = CuratorCacheListener.builder().forPathChildrenCache(path, curatorFramework, pathChildrenCacheListener).build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }
}
