package Cache;


import Cache.CachingStrategy.CachingStrategy;
import Cache.CachingStrategy.LRUCachingStrategy;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LazyCache implements CacheInterface {
    private static final long DEFAULT_TIME_TO_LIVE = 1000L;
    private static final int DEFAULT_MAX_CACHE_SIZE = 1000;

    private final Map<String, CacheEntryInterface> cacheMap;
    private final long defaultTimeToLive;
    private final CachingStrategy cachingStrategy;

    public LazyCache() {
        this(DEFAULT_TIME_TO_LIVE, new LRUCachingStrategy(DEFAULT_MAX_CACHE_SIZE));
    }

    public LazyCache(long defaultTimeToLive) {
        this(defaultTimeToLive, new LRUCachingStrategy(DEFAULT_MAX_CACHE_SIZE));
    }

    public LazyCache(int maxCacheSize) {
        this(DEFAULT_TIME_TO_LIVE, new LRUCachingStrategy(maxCacheSize));
    }

    public LazyCache(CachingStrategy cachingStrategy) {
        this(DEFAULT_TIME_TO_LIVE, cachingStrategy);
    }

    public LazyCache(long defaultTimeToLive, int maxCacheSize) {
        this(defaultTimeToLive, new LRUCachingStrategy(maxCacheSize));
    }

    public LazyCache(long defaultTimeToLive, CachingStrategy cachingStrategy) {
        this.cacheMap = new ConcurrentHashMap<>();
        this.defaultTimeToLive = defaultTimeToLive;
        this.cachingStrategy = cachingStrategy;
    }

    /**
     * Adds a new value to the cache.
     * Delegates to the overloaded put method with default time to live.
     *
     * @param key   The key to add the value for.
     * @param value The value to add.
     */
    @Override
    public void put(String key, Object value) {
        put(key, value, defaultTimeToLive);
    }

    /**
     * Adds a new value to the cache.
     * - If the key is already present in the cache, the value is updated.
     * - If the key is not present in the cache, the value is added to the cache.
     * - If the cache is full, a cleanup done by the caching strategy/mechanism is performed before adding the new value.
     *
     * @param key        The key to add the value for.
     * @param value      The value to add.
     * @param timeToLive The time to live for the value in milliseconds.
     */
    @Override
    public void put(String key, Object value, long timeToLive) {
        // Cleanup the cache before adding a new value.
        cachingStrategy.cleanup(cacheMap);

        // Generating a new CacheEntry and adding it to the cache.
        CacheEntry cacheEntry = new CacheEntry(value, System.currentTimeMillis() + timeToLive);
        cacheMap.put(key, cacheEntry);

        // Update the access order of the key.
        cachingStrategy.updateAccessOrder(key);
    }

    /**
     * Returns the value associated with the key.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key,
     * or an empty Optional if:
     * 1. The key is not present in the cache.
     * 2. The key is present in the cache, but the entry has expired.
     * In this case, the key is removed from the cache.
     */
    @Override
    public Optional<Object> get(String key) {
        // Cleanup the cache before retrieving the value.
        cachingStrategy.cleanup(cacheMap);

        // Get the CacheEntry from the cache.
        CacheEntryInterface cacheEntry = cacheMap.get(key);

        // If the key is not present in the cache, return an empty Optional.
        if (cacheEntry == null) {
            // Remove the key from the caching strategy.
            cachingStrategy.remove(key);
            return Optional.empty();
        }

        // If the key is present in the cache, but the value has expired, remove the key from the cache and return an empty Optional.
        if (cacheEntry.hasExpired()) {
            this.remove(key);
            return Optional.empty();
        }

        // If the key is present in the cache and the value has not expired, return the value.
        return Optional.of(cacheEntry.getValue());
    }

    /**
     * Removes the key from the cache.
     * If the key is present in the cache, it is removed from the cache.
     * If the key is not present in the cache, nothing happens.
     *
     * @param key The key to remove from the cache.
     */
    @Override
    public void remove(String key) {
        cachingStrategy.remove(key);
        cacheMap.remove(key);
    }
}
