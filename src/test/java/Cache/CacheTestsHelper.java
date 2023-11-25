package Cache;

import java.lang.reflect.Field;
import java.util.Map;

public class CacheTestsHelper {
    public static CacheEntryInterface createCacheEntry(Object value, long expiresAt) {
        return new CacheEntry(value, expiresAt);
    }

    public static CacheEntryInterface createCacheEntry(Object value) {
        return new CacheEntry(value, System.currentTimeMillis() + 1000L);
    }

    @SuppressWarnings("unchecked")
     static Map<String, CacheEntryInterface> getPrivateCacheMap(CacheInterface lazyCache) throws NoSuchFieldException, IllegalAccessException {
        Field cacheMapField = LazyCache.class.getDeclaredField("cacheMap");
        cacheMapField.setAccessible(true);
         return (Map<String, CacheEntryInterface>) cacheMapField.get(lazyCache);
    }
}
