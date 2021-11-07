package test;

import main.Cache;
import main.LFUCache;
import main.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static test.TestData.*;

public class LFUCacheTest {

    Cache cache;
    private final static int CACHE_CAPACITY = 3;
    Integer elemNotExist = -1;

    @Before
    public void setUp() {
        cache = new LFUCache(CACHE_CAPACITY);
    }

    @Test
    public void testLRUCache() {

        cache.put(p1);
        cache.put(p2);
        cache.put(p3);

        Person[] data = new Person[]{p3, p1, p2};

        Assert.assertArrayEquals(data, cache.getElemFromLRUCache());

        cache.get(p1);
        cache.put(p4);

        data = new Person[]{p3, p1, p4};
        Assert.assertArrayEquals(data, cache.getElemFromLRUCache());

        cache.get(p1);
        cache.get(p1);
        cache.get(p3);
        Assert.assertEquals(elemNotExist, cache.get(p2));

        cache.put(p5);
        cache.put(p6);

        Assert.assertEquals(elemNotExist, cache.get(p4));

        data = new Person[]{p3, p1, p6};
        Assert.assertArrayEquals(data, cache.getElemFromLRUCache());
    }

    @Test
    public void getElemNotExist() {
        Assert.assertEquals(elemNotExist, cache.get((p10)));
    }
}
