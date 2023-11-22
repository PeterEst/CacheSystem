package Cache;

public interface CacheInterface {
    void put(String key, Object value);

    void put(String key, Object value, long timeToLive);

    Object get(String key);

    void remove(String key);
}
