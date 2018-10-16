/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest;

import cachetest.type.CacheLFU;
import cachetest.type.CacheLRU;
import java.util.Map;

/**
 * The class implements the creation of a specific type of cache
 *
 * @author kentyku
 */
public class ClientCache {

    private cachetest.type.Cache cache;
    private final int size;

    /**
     * Сonstructor of class CacheBuilder
     *
     * @param typeCache
     * @param sizeCache
     * @param typeStore Type
     */
    ClientCache(TypeCache typeCache, int sizeCache, TypeStore typeStore) {//применить патерн билдер
        this.size = sizeCache;
        switch (typeCache) {

            case LRU:
                this.cache = new CacheLRU(size, typeStore);
                break;
            case LFU:
                this.cache = new CacheLFU(size, typeStore);
                break;
        }

        DirectorCache dir = new DirectorCache();
        LRUBuilder builderLRU = new LRUBuilder();

        dir.constructCacheHDD(builderLRU);
        CacheLRU cacheHDDLRU = builderLRU.getCacheLRU();

        dir.constructCacheRAM(builderLRU);
        CacheLRU cacheRAMLRU = builderLRU.getCacheLRU();

        LFUBuilder builderLFU = new LFUBuilder();

        dir.constructCacheHDD(builderLFU);
        CacheLFU cacheHDDLFU = builderLFU.getCacheLFU();

        dir.constructCacheRAM(builderLFU);
        CacheLFU cacheRAMLFU = builderLFU.getCacheLFU();
    }

    /**
     * Adding data to the cache
     *
     * @param key unique key
     * @param data value
     */
    void addData(int key, String data) {
        cache.addData(key, data);
    }

    /**
     * Getting data from the cache by key
     *
     * @param key unique key
     * @return
     */
    String getData(int key) {
        return cache.getData(key);
    }

    /**
     * Reset cache
     */
    void resetCash() {
        cache.resetStoreCache();
    }

    /**
     * View values ​​in the cache
     */
    void showData() {

        for (Map.Entry<Integer, String> entry : cache.getCache().entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }
}
