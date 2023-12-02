package Cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheEntryTest {
    @Test
    public void getValue_CacheEntryWithValue_ReturnsValue() {
        // Arrange
        String value = "value";
        CacheEntry cacheEntry = new CacheEntry(value, 0);

        // Act
        Object actualValue = cacheEntry.getValue();

        // Assert
        Assertions.assertEquals(value, actualValue);
    }

    @Test
    public void getValue_CacheEntryWithoutValue_ReturnsNull() {
        // Arrange
        CacheEntry cacheEntry = new CacheEntry(null, 0);

        // Act
        Object actualValue = cacheEntry.getValue();

        // Assert
        Assertions.assertNull(actualValue);
    }

    @Test
    public void getExpirationTimestamp_CacheEntryWithExpirationTimestamp_ReturnsExpirationTimestamp() {
        // Arrange
        long expirationTimestamp = 0;
        CacheEntry cacheEntry = new CacheEntry("value", expirationTimestamp);

        // Act
        long actualExpirationTimestamp = cacheEntry.getExpirationTimestamp();

        // Assert
        Assertions.assertEquals(expirationTimestamp, actualExpirationTimestamp);
    }

    @Test
    public void hasExpired_CacheEntryWithExpirationTimestamp_ReturnsTrue() {
        // Arrange
        long expirationTimestamp = 0;
        CacheEntry cacheEntry = new CacheEntry("value", expirationTimestamp);

        // Act
        boolean hasExpired = cacheEntry.hasExpired();

        // Assert
        Assertions.assertTrue(hasExpired);
    }

    @Test
    public void hasExpired_CacheEntryWithoutExpirationTimestamp_ReturnsFalse() {
        // Arrange
        CacheEntry cacheEntry = new CacheEntry("value", System.currentTimeMillis() + 1000L);

        // Act
        boolean hasExpired = cacheEntry.hasExpired();

        // Assert
        Assertions.assertFalse(hasExpired);
    }
}
