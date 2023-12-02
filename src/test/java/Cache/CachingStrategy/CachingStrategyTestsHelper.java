package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.Map;

public class CachingStrategyTestsHelper {
    @SuppressWarnings("unchecked")
    static Deque<String> getLRUPrivateAccessOrder(LRUCachingStrategy cachingStrategy) throws NoSuchFieldException, IllegalAccessException {
        Field accessOrderField = LRUCachingStrategy.class.getDeclaredField("accessOrder");
        accessOrderField.setAccessible(true);
        return (Deque<String>) accessOrderField.get(cachingStrategy);
    }

    @SuppressWarnings("unchecked")
    static Deque<String> getMRUPrivateAccessOrder(MRUCachingStrategy cachingStrategy) throws NoSuchFieldException, IllegalAccessException {
        Field accessOrderField = MRUCachingStrategy.class.getDeclaredField("accessOrder");
        accessOrderField.setAccessible(true);
        return (Deque<String>) accessOrderField.get(cachingStrategy);
    }

    @SuppressWarnings("unchecked")
    static Map<String, Integer> getLFUPrivateFrequencyMap(LFUCachingStrategy cachingStrategy) throws NoSuchFieldException, IllegalAccessException {
        Field accessOrderField = LFUCachingStrategy.class.getDeclaredField("frequencyMap");
        accessOrderField.setAccessible(true);
        return (Map<String, Integer>) accessOrderField.get(cachingStrategy);
    }

    static void fillAccessOrder(Deque<String> accessOrder, Map<String, CacheEntryInterface> map) {
        for (String key : map.keySet()) {
            accessOrder.addFirst(key);
        }
    }
}
