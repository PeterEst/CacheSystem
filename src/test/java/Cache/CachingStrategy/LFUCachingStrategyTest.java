package Cache.CachingStrategy;

import Cache.CacheEntryInterface;
import Cache.CacheTestsHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class LFUCachingStrategyTest {
    private LFUCachingStrategy lfuCachingStrategy;
    private Map<String, Integer> frequencyMap;
    private static final int MAX_CACHE_SIZE = 3;


    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        lfuCachingStrategy = new LFUCachingStrategy(MAX_CACHE_SIZE);
        frequencyMap = CachingStrategyTestsHelper.getLFUPrivateFrequencyMap(lfuCachingStrategy);
    }

    @Test
    public void cleanup_CacheMapExceedsMaxSize_RemovesLeastFrequentlyUsed() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap
        cacheMap.put("1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("2", CacheTestsHelper.createCacheEntry("value2"));
        cacheMap.put("3", CacheTestsHelper.createCacheEntry("value3"));
        cacheMap.put("4", CacheTestsHelper.createCacheEntry("value4"));

        // Fill frequencyMap
        frequencyMap.put("1", 1);
        frequencyMap.put("2", 1);
        frequencyMap.put("3", 1);
        frequencyMap.put("4", 1);

        // Invoke cleanup
        lfuCachingStrategy.cleanup(cacheMap);

        // Check if least frequently used key is removed
        Assertions.assertFalse(cacheMap.containsKey("1"));
    }

    @Test
    public void cleanup_CacheMapExceedsMaxSize2_RemovesLeastFrequentlyUsed() {
        Map<String, CacheEntryInterface> cacheMap = new HashMap<>();

        // Add entries to cacheMap | 1 -> 2 -> 3 -> 4
        cacheMap.put("1", CacheTestsHelper.createCacheEntry("value1"));
        cacheMap.put("2", CacheTestsHelper.createCacheEntry("value2"));
        cacheMap.put("3", CacheTestsHelper.createCacheEntry("value3"));
        cacheMap.put("4", CacheTestsHelper.createCacheEntry("value4"));

        // Add frequencies
        frequencyMap.put("1", 2);
        frequencyMap.put("2", 1);
        frequencyMap.put("3", 1);
        frequencyMap.put("4", 1);

        // Invoke cleanup
        lfuCachingStrategy.cleanup(cacheMap);

        // Check if least frequently used key is removed | 1 -> 3 -> 4
        Assertions.assertTrue(cacheMap.containsKey("1"));
        Assertions.assertFalse(cacheMap.containsKey("2"));
        Assertions.assertTrue(cacheMap.containsKey("3"));
    }

    @Test
    public void updateCacheState_KeyExists_ShouldIncrementFrequency() {
        // Add frequencies
        frequencyMap.put("1", 1);
        frequencyMap.put("2", 1);
        frequencyMap.put("3", 1);

        // Invoke updateCacheState
        lfuCachingStrategy.updateCacheState("1");
        lfuCachingStrategy.updateCacheState("2");
        lfuCachingStrategy.updateCacheState("2");
        lfuCachingStrategy.updateCacheState("2");

        // Check if frequency is incremented
        Assertions.assertEquals(2, frequencyMap.get("1"));
        Assertions.assertEquals(4, frequencyMap.get("2"));
    }

    @Test
    public void remove_KeyExists_ShouldRemoveFromFrequencyMap() {
        // Add frequencies
        frequencyMap.put("1", 1);
        frequencyMap.put("2", 1);
        frequencyMap.put("3", 1);

        // Invoke remove
        lfuCachingStrategy.remove("1");

        // Check if key1 is removed from frequency map
        Assertions.assertFalse(frequencyMap.containsKey("1"));
    }
}
