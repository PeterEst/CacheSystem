package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public class MRUCachingStrategy implements CachingStrategyInterface {
    private final int maxCacheSize;

    private final Deque<String> accessOrder;

    public MRUCachingStrategy(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
        this.accessOrder = new LinkedList<>();
    }

    @Override
    public void cleanup(Map<String, CacheEntryInterface> cacheMap) {
        while (cacheMap.size() > maxCacheSize) {
            String mostRecentlyUsedKey = accessOrder.removeFirst();
            cacheMap.remove(mostRecentlyUsedKey);
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
