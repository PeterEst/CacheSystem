package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public abstract class CachingStrategy {
    protected final Deque<String> accessOrder;
    protected final int maxCacheSize;

    protected CachingStrategy(int maxCacheSize) {
        this.accessOrder = new LinkedList<>();
        this.maxCacheSize = maxCacheSize;
    }

    public abstract void cleanup(Map<String, CacheEntryInterface> cacheMap);

    public abstract void updateAccessOrder(String key);

    public abstract void remove(String key);
}
