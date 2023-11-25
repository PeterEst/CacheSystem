package Cache;

public interface CacheEntryInterface {
    Object getValue();

    long getExpirationTimestamp();

    boolean hasExpired();
}
