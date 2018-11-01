/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

import cachetest.type.CacheBuilder;
import cachetest.type.Cache;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jury
 */
public class CacheTestTest {

    Cache cache;
    int size = 5;

    public CacheTestTest() {
    }

    @Test
    public void testCacheLRURAM() {

        cache = new CacheBuilder(CacheType.LRU, size, StoreType.RAM).getCacheObject();
        fillCache();
        Integer[] actuals = cache.getCache().keySet().toArray(new Integer[size]);
        Integer[] expecteds = {2, 3, 4, 6, 7};
        assertArrayEquals("Unexpected entries cache", expecteds, actuals);
        assertFalse((new File("cacheLru.data")).exists());
    }

    @Test
    public void testCacheLRUHDD() {
        cache = new CacheBuilder(CacheType.LRU, size, StoreType.HDD).getCacheObject();
        fillCache();
        Integer[] actuals = cache.getCache().keySet().toArray(new Integer[size]);
        Integer[] expecteds = {2, 3, 4, 6, 7};
        assertArrayEquals("Unexpected entries cache", expecteds, actuals);
        assertTrue((new File("cacheLru.data")).exists());
        cache.resetStoreCache();
        assertFalse((new File("cacheLru.data")).exists());
        cache.addData(8, "Киев");
        actuals = cache.getCache().keySet().toArray(new Integer[size]);
        expecteds = new Integer[size];
        expecteds[0] = 8;
        assertArrayEquals("Unexpected entries cache", expecteds, actuals);
        cache.resetStoreCache();
        assertFalse((new File("cacheLru.data")).exists());
    }

    @Test
    public void testCacheLFURAM() {
        cache = new CacheBuilder(CacheType.LFU, size, StoreType.RAM).getCacheObject();
        fillCache();
        Integer[] actuals = cache.getCache().keySet().toArray(new Integer[size]);
        Integer[] expecteds = {3, 4, 5, 6, 7};
        assertArrayEquals("Unexpected entries cache", expecteds, actuals);
        assertFalse((new File("cacheLfu.data")).exists());
    }

    @Test
    public void testCacheLFUHDD() {
        cache = new CacheBuilder(CacheType.LFU, size, StoreType.HDD).getCacheObject();
        fillCache();
        Integer[] actuals = cache.getCache().keySet().toArray(new Integer[size]);
        Integer[] expecteds = {3, 4, 5, 6, 7};
        assertArrayEquals("Unexpected entries cache", expecteds, actuals);
        assertTrue((new File("cacheLfu.data")).exists());
        cache.resetStoreCache();
        assertFalse((new File("cacheLfu.data")).exists());
        cache.addData(8, "Киев");
        actuals = cache.getCache().keySet().toArray(new Integer[size]);
        expecteds = new Integer[size];
        expecteds[0] = 8;
        assertArrayEquals("Unexpected entries cache", expecteds, actuals);
        cache.resetStoreCache();
        assertFalse((new File("cacheLfu.data")).exists());

    }

    public void fillCache() {
        cache.addData(1, "Ижевск");
        cache.addData(2, "Лондон");
        cache.addData(3, "Венеция");
        cache.addData(4, "Берлин");
        cache.addData(5, "Вашингтон");
        cache.getData(5);
        cache.getData(5);
        cache.getData(5);
        cache.getData(1);
        cache.getData(2);
        cache.getData(2);
        cache.getData(3);
        cache.getData(3);
        cache.getData(4);
        cache.getData(4);
        cache.addData(6, "Токио");
        cache.getData(6);
        cache.getData(6);
        cache.getData(6);
        cache.addData(7, "Париж");
    }

}
