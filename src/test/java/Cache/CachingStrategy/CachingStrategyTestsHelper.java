package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.Map;

public class CachingStrategyTestsHelper {
    @SuppressWarnings("unchecked")
    static Deque<String> getPrivateAccessOrder(CachingStrategy cachingStrategy) throws NoSuchFieldException, IllegalAccessException {
        Field accessOrderField = CachingStrategy.class.getDeclaredField("accessOrder");
        accessOrderField.setAccessible(true);
        return (Deque<String>) accessOrderField.get(cachingStrategy);
    }

    static void fillAccessOrder(Deque<String> accessOrder, Map<String, CacheEntryInterface> map) {
        for (String key : map.keySet()) {
            accessOrder.addFirst(key);
        }
    }
}
