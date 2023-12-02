package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public class MRUCachingStrategy extends CachingStrategy {
    protected final Deque<String> accessOrder;

    public MRUCachingStrategy(int maxCacheSize) {
        super(maxCacheSize);
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
    public void updateCacheState(String key) {
        accessOrder.remove(key);
        accessOrder.addFirst(key);
    }

    @Override
    public void remove(String key) {
        accessOrder.remove(key);
    }
}
