package nl.knaake.erik.unittests;

import nl.knaake.erik.datasourcelayer.CacheIdentityMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CacheTest {

    @BeforeEach
    void init() {
        CacheIdentityMap.clearInstances();
    }

    @Test
    void addToCacheAndGet() {
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        testCache.put("test1", 2);
        assertEquals(2, testCache.get("test1"));
    }

    @Test
    void newInstanceForNewType() {
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        assertEquals(1, CacheIdentityMap.getNumberOfInstances());
        CacheIdentityMap testCache2 = CacheIdentityMap.getInstance(String.class, String.class);
        assertEquals(2, CacheIdentityMap.getNumberOfInstances());
    }

    @Test
    void onlyOneInstancePerType() {
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        assertEquals(1, CacheIdentityMap.getNumberOfInstances());
        CacheIdentityMap testCache2 = CacheIdentityMap.getInstance(String.class, Integer.class);
        assertEquals(1, CacheIdentityMap.getNumberOfInstances());
    }

    @Test
    void getNonExisting() {
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        assertNull(testCache.get("test1"));
    }

    @Test
    void updateExisting() {
        String key = "test1";
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        testCache.put(key, 2);
        testCache.update(key, 3);
        assertEquals(3, testCache.get(key));
    }

    @Test
    void updateNonExisting() {
        String key = "test1";
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        testCache.update(key, 3);
        assertEquals(3, testCache.get(key));
    }

    @Test
    void delete() {
        String key = "test1";
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        testCache.put(key, 2);
        testCache.remove(key);
        assertNull(testCache.get(key));
    }

    @Test
    void deleteNonExisting() {
        String key = "test1";
        CacheIdentityMap testCache = CacheIdentityMap.getInstance(String.class, Integer.class);
        testCache.remove(key);
        assertNull(testCache.get(key));
    }

}
