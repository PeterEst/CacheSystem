package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Map;

public class MRUCachingStrategy extends CachingStrategy {
    public MRUCachingStrategy(int maxCacheSize) {
        super(maxCacheSize);
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
