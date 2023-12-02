package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public abstract class CachingStrategy {
    protected final int maxCacheSize;

    protected CachingStrategy(int maxCacheSize) {
        if (maxCacheSize <= 1) {
            throw new IllegalArgumentException("maxCacheSize must be greater than 1");
        }

        this.maxCacheSize = maxCacheSize;
    }

    /**
     * Cleans up the cache map according to the caching strategy.
     */
    public abstract void cleanup(Map<String, CacheEntryInterface> cacheMap);

    /**
     * Updates the access order according to the caching strategy.
     */
    public abstract void updateCacheState(String key);

    /**
     * Removes the key from the access order.
     */
    public abstract void remove(String key);
}
