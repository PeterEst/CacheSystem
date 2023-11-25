package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Map;

public class LRUCachingStrategy extends CachingStrategy {
    public LRUCachingStrategy(int maxCacheSize) {
        super(maxCacheSize);
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
