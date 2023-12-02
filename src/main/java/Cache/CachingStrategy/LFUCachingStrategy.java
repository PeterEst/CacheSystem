package Cache.CachingStrategy;

import Cache.CacheEntryInterface;

import java.util.Map;
import java.util.TreeMap;

public class LFUCachingStrategy extends CachingStrategy{
    protected final Map<String, Integer> frequencyMap;

    public LFUCachingStrategy(int maxCacheSize) {
        super(maxCacheSize);
        this.frequencyMap = new TreeMap<>();
    }

    private String getLeastFrequentlyUsedKey() {
        int minFrequency = Integer.MAX_VALUE;
        String leastFrequentlyUsedKey = null;

        for (String key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (frequency < minFrequency) {
                minFrequency = frequency;
                leastFrequentlyUsedKey = key;
            }
        }

        return leastFrequentlyUsedKey;
    }

    @Override
    public void cleanup(Map<String, CacheEntryInterface> cacheMap) {
        while (cacheMap.size() > maxCacheSize) {
            String leastFrequentlyUsedKey = getLeastFrequentlyUsedKey();
            frequencyMap.remove(leastFrequentlyUsedKey);
            cacheMap.remove(leastFrequentlyUsedKey);
        }
    }

    @Override
    public void updateCacheState(String key) {
        frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
    }

    @Override
    public void remove(String key) {
        frequencyMap.remove(key);
    }
}
