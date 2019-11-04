package nl.knaake.erik.performancetests;

import nl.knaake.erik.datasourcelayer.CacheIdentityMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheBenchmarks {

    @BeforeEach
    void init() {
        CacheIdentityMap.clearInstances();
    }

    @Test
    void put10000Different() {
        CacheIdentityMap cache = CacheIdentityMap.getInstance(Integer.class, Integer.class);
        long start = System.currentTimeMillis();
        for(int i =  0; i < 10000; i++) {
            cache.put(i, i);
        }
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("Time for putting 10000: " + totalTime + " millis");
        assertTrue(totalTime < 300);
    }

    @Test
    void update10000Times() {
        CacheIdentityMap cache = CacheIdentityMap.getInstance(Integer.class, Integer.class);
        for(int i =  0; i < 10000; i++) {
            cache.put(i, i);
        }

        long start = System.currentTimeMillis();
        for(int i =  0; i < 10000; i++) {
            cache.update(i, i + 1);
        }
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("Time for updating 10000 times: " + totalTime + " millis");
        assertTrue(totalTime < 200);
    }

    @Test
    void getInstanceFor1Type10000Times() {
        CacheIdentityMap cache = CacheIdentityMap.getInstance(Integer.class, Integer.class);
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
             cache = CacheIdentityMap.getInstance(Integer.class, Integer.class);
        }
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("Time for getting 1 type instance 10000 times: " + totalTime + " millis");
        //Compiler may otherwise optimize the loop away
        cache.put(1, 1);
        assertEquals(1, cache.get(1));
        assertTrue(totalTime < 30);
    }

    @Test
    void getInstanceFor1TypeInListOf16Types10000Times() {
        CacheIdentityMap cache = CacheIdentityMap.getInstance(Integer.class, Integer.class);
        CacheIdentityMap.getInstance(String.class, Integer.class);
        CacheIdentityMap.getInstance(Object.class, Integer.class);
        CacheIdentityMap.getInstance(Class.class, Integer.class);
        CacheIdentityMap.getInstance(Integer.class, String.class);
        CacheIdentityMap.getInstance(String.class, String.class);
        CacheIdentityMap.getInstance(Object.class, String.class);
        CacheIdentityMap.getInstance(Class.class, String.class);
        CacheIdentityMap.getInstance(Integer.class, Object.class);
        CacheIdentityMap.getInstance(String.class, Object.class);
        CacheIdentityMap.getInstance(Object.class, Object.class);
        CacheIdentityMap.getInstance(Class.class, Object.class);

        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            cache = CacheIdentityMap.getInstance(Integer.class, String.class);
        }
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("Time for getting 1 type instance out of 16 types 10000 times: " + totalTime + " millis");
        //Compiler may otherwise optimize the loop away
        cache.put(1, "1");
        assertEquals("1", cache.get(1));
        assertTrue(totalTime < 50);
    }

}
