package Cache;

import java.util.Optional;

public interface CacheInterface {
    void put(String key, Object value);

    void put(String key, Object value, long timeToLive);

    Optional<Object> get(String key);

    void remove(String key);
}
