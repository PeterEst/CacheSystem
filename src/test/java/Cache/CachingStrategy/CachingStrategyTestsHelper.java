package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.Map;

public class CachingStrategyTestsHelper {
    @SuppressWarnings("unchecked")
    static Deque<String> getPrivateAccessOrder(CachingStrategyInterface mruCachingStrategy) throws NoSuchFieldException, IllegalAccessException {
        Field accessOrderField = MRUCachingStrategy.class.getDeclaredField("accessOrder");
        accessOrderField.setAccessible(true);
        return (Deque<String>) accessOrderField.get(mruCachingStrategy);
    }

    static void fillAccessOrder(Deque<String> accessOrder, Map<String, CacheEntryInterface> map) {
        for (String key : map.keySet()) {
            accessOrder.addFirst(key);
        }
    }
}
