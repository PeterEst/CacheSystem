package Cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class LazyCacheTest {
    private LazyCache lazyCache;
    Map<String, CacheEntryInterface> cacheMap;

    private Map<String, CacheEntryInterface> getCacheMap() throws NoSuchFieldException, IllegalAccessException {
        Field cacheMapField = LazyCache.class.getDeclaredField("cacheMap");
        cacheMapField.setAccessible(true);
        return (Map<String, CacheEntryInterface>) cacheMapField.get(lazyCache);
    }

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        lazyCache = new LazyCache();
        Field cacheMapField = LazyCache.class.getDeclaredField("cacheMap");
        cacheMapField.setAccessible(true);
        cacheMap = getCacheMap();
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

        cacheMap.put(key, new CacheEntry(value, System.currentTimeMillis() + 1000));

        Optional<Object> cachedValue = lazyCache.get(key);

        Assertions.assertTrue(cachedValue.isPresent());
        Assertions.assertEquals(Optional.of(value), cachedValue);
    }
}
