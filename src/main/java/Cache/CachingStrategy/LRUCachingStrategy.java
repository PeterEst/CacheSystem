package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public class LRUCachingStrategy implements CachingStrategyInterface {
    private final Deque<String> accessOrder;
    private final int maxCacheSize;


    public LRUCachingStrategy(int maxCacheSize) {
        this.accessOrder = new LinkedList<>();
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    public void cleanup(Map<String, CacheEntryInterface> cacheMap) {
        while (cacheMap.size() > maxCacheSize) {
            String leastRecentlyUsedKey = accessOrder.removeLast();
            cacheMap.remove(leastRecentlyUsedKey);
        }
    }

    @Override
    public void updateAccessOrder(String key) {
        accessOrder.remove(key);
        accessOrder.addFirst(key);
    }

    @Override
    public void remove(String key) {
        accessOrder.remove(key);
    }
}
