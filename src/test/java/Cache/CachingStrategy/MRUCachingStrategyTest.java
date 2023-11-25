package Cache.CachingStrategy;

import Cache.CacheEntryInterface;
import Cache.CacheTestsHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MRUCachingStrategyTest {
    private MRUCachingStrategy mruCachingStrategy;
    private Deque<String> accessOrder;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        mruCachingStrategy = new MRUCachingStrategy(3);
        accessOrder = CachingStrategyTestsHelper.getPrivateAccessOrder(mruCachingStrategy);
    }

    @Test
    public void cleanup_CacheMapExceedsMaxSize_RemovesMostRecentlyUsed() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("key1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("key2", CacheTestsHelper.createCacheEntry("value2"));
        cacheMap.put("key3", CacheTestsHelper.createCacheEntry("value3"));
        cacheMap.put("key4", CacheTestsHelper.createCacheEntry("value4"));

        // Fill accessOrder
        CachingStrategyTestsHelper.fillAccessOrder(accessOrder, cacheMap);

        // Invoke cleanup
        mruCachingStrategy.cleanup(cacheMap);

        // Check if most recently used key is removed
        Assertions.assertFalse(cacheMap.containsKey("key4"));
    }

    @Test
    public void updateAccessOrder_KeyExists_ShouldMoveKeyToFirst() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("key1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("key2", CacheTestsHelper.createCacheEntry("value2"));

        // Fill accessOrder
        CachingStrategyTestsHelper.fillAccessOrder(accessOrder, cacheMap);

        // Invoke updateAccessOrder
        mruCachingStrategy.updateAccessOrder("key1");

        // Check if key1 is moved to first
        Assertions.assertEquals("key1", accessOrder.getFirst());
    }

    @Test
    public void remove_KeyExists_ShouldRemoveKeyFromAccessOrder() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("key1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("key2", CacheTestsHelper.createCacheEntry("value2"));

        // Fill accessOrder
        CachingStrategyTestsHelper.fillAccessOrder(accessOrder, cacheMap);

        // Invoke remove
        mruCachingStrategy.remove("key1");

        // Check if key1 is removed from accessOrder
        Assertions.assertFalse(accessOrder.contains("key1"));
    }
}
