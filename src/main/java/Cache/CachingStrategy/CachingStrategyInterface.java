package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Map;

public interface CachingStrategyInterface {
    void cleanup(Map<String, CacheEntryInterface> cacheMap);

    void updateAccessOrder(String key);

    void remove(String key);
}
