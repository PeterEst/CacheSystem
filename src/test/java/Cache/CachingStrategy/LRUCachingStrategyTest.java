package Cache.CachingStrategy;

import Cache.CacheEntryInterface;
import Cache.CacheTestsHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class LRUCachingStrategyTest {

    private LRUCachingStrategy lruCachingStrategy;
    private Deque<String> accessOrder;
    private static final int MAX_CACHE_SIZE = 3;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Initialize with a max size of 3 for testing purposes
        lruCachingStrategy = new LRUCachingStrategy(MAX_CACHE_SIZE);

        // Get accessOrder
        accessOrder = CachingStrategyTestsHelper.getLRUPrivateAccessOrder(lruCachingStrategy);
    }

    @Test
    public void cleanup_CacheMapExceedsMaxSize_RemovesLeastRecentlyUsed() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("2", CacheTestsHelper.createCacheEntry("value2"));
        cacheMap.put("3", CacheTestsHelper.createCacheEntry("value3"));
        cacheMap.put("4", CacheTestsHelper.createCacheEntry("value4"));

        // Fill accessOrder
        CachingStrategyTestsHelper.fillAccessOrder(accessOrder, cacheMap);

        // Invoke cleanup
        lruCachingStrategy.cleanup(cacheMap);

        // Check if least recently used key is removed
        Assertions.assertFalse(cacheMap.containsKey("1"));
    }

    @Test
    public void updateCacheState_KeyExists_ShouldMoveKeyToFirst() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("2", CacheTestsHelper.createCacheEntry("value2"));

        // Fill accessOrder
        CachingStrategyTestsHelper.fillAccessOrder(accessOrder, cacheMap);

        // Invoke updateCacheState
        lruCachingStrategy.updateCacheState("1");

        // Check if key1 is moved to the front
        LinkedList<String> expectedOrder = new LinkedList<>();
        expectedOrder.addFirst("1");
        expectedOrder.addLast("2");

        Assertions.assertEquals(expectedOrder, new LinkedList<>(accessOrder));
    }

    @Test
    public void remove_KeyExists_ShouldRemoveFromAccessOrder() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("2", CacheTestsHelper.createCacheEntry("value2"));

        // Add entries to accessOrder
        CachingStrategyTestsHelper.fillAccessOrder(accessOrder, cacheMap);

        // Invoke remove
        lruCachingStrategy.remove("1");

        // Check if key1 is removed from access order
        LinkedList<String> expectedOrder = new LinkedList<>();
        expectedOrder.addLast("2");

        Assertions.assertEquals(expectedOrder, new LinkedList<>(accessOrder));
    }
}