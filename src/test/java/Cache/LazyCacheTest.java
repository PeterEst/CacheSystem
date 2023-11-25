package Cache;

import Utils.GeneralUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

public class LazyCacheTest {
    private LazyCache lazyCache;
    Map<String, CacheEntryInterface> cacheMap;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Initialize LazyCache
        lazyCache = new LazyCache();

        // Get cacheMap
        cacheMap = CacheTestsHelper.getPrivateCacheMap(lazyCache);
    }

    @Test
    public void put_AddNewValueToCache_CacheContainsValue() {
        String key = "key";
        String value = "value";

        lazyCache.put(key, value);

        Assertions.assertTrue(cacheMap.containsKey(key));
        Assertions.assertEquals(value, cacheMap.get(key).getValue());
    }

    @Test
    public void put_UpdateExistingValueInCache_CacheContainsUpdatedValue() {
        String key = "key";
        String value = "value";

        String updatedValue = "updatedValue";

        lazyCache.put(key, value);
        lazyCache.put(key, updatedValue);

        Assertions.assertTrue(cacheMap.containsKey(key));
        Assertions.assertEquals(updatedValue, cacheMap.get(key).getValue());
    }

    @Test
    public void get_ValueNotInCache_ReturnsEmptyOptional() {
        String key = "key";

        Optional<Object> cachedValue = lazyCache.get(key);

        Assertions.assertFalse(cachedValue.isPresent());
    }

    @Test
    public void get_ValueInCache_ReturnsValue() {
        String key = "key";
        String value = "value";
        long timeToLive = 1000L;
        long expiryTime = System.currentTimeMillis() + timeToLive;

        cacheMap.put(key, new CacheEntry(value, expiryTime));

        Optional<Object> cachedValue = lazyCache.get(key);

        Assertions.assertTrue(cachedValue.isPresent());
        Assertions.assertEquals(Optional.of(value), cachedValue);
    }

    @Test
    public void get_ValueExpired_ReturnsEmptyOptional() {
        String key = "key";
        String value = "value";
        long timeToLive = 1000L;
        long expiryTime = System.currentTimeMillis() + timeToLive;

        cacheMap.put(key, new CacheEntry(value, expiryTime));

        GeneralUtils.wait(1000);

        Optional<Object> cachedValue = lazyCache.get(key);

        Assertions.assertFalse(cachedValue.isPresent());
    }

    @Test
    public void get_KeyNotFound_ReturnsEmptyOptional() {
        String key = "key";

        Optional<Object> cachedValue = lazyCache.get(key);

        Assertions.assertFalse(cachedValue.isPresent());
    }

    @Test
    public void remove_ValueRemoved_ReturnsEmptyOptional() {
        String key = "key";
        String value = "value";
        long timeToLive = 1000L;
        long expiryTime = System.currentTimeMillis() + timeToLive;

        cacheMap.put(key, new CacheEntry(value, expiryTime));

        lazyCache.remove(key);

        Assertions.assertFalse(cacheMap.containsKey(key));
    }

    @Test
    public void remove_KeyNotFound_ReturnsEmptyOptional() {
        String key = "key";

        lazyCache.remove(key);

        Assertions.assertFalse(cacheMap.containsKey(key));
    }
}
