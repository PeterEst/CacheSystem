package Cache;

class CacheEntry implements CacheEntryInterface {
    private final Object value;
    private final long expirationTimestamp;

    public CacheEntry(Object value, long expiresAt) {
        this.value = value;
        this.expirationTimestamp = expiresAt;
    }

    public Object getValue() {
        return value;
    }

    public long getExpirationTimestamp() {
        return expirationTimestamp;
    }

    @Override
    public boolean hasExpired() {
        return System.currentTimeMillis() > expirationTimestamp;
    }

    @Override
    public String toString() {
        return "CacheEntry{" + "value=" + value + ", expirationTimestamp=" + expirationTimestamp + '}';
    }
}
