/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.CacheType;
import cachetest.StoreType;


/**
 * The class implements the creation of a specific type of cache
 *
 * @author kentyku
 */
public class CacheBuilder {

    private Cache cache;

    public CacheBuilder(CacheType cacheType, int size, StoreType storeType) {
        switch (cacheType) {
            case LRU:
                this.cache = new LRUCache(size, storeType);
                break;
            case LFU:
                this.cache = new LFUCache(size, storeType);
                break;
            default:
        }

    }

    public Cache getCacheObject() {
        return this.cache;
    }
}
