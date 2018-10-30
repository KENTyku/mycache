/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest;

import cachetest.type.Cache;
import cachetest.type.LFUCache;
import cachetest.type.LRUCache;

/**
 * The class implements the creation of a specific type of cache
 *
 * @author kentyku
 */
public class CacheBuilder {

    private Cache cache;
    private final int size;    
    
    CacheBuilder(TypeCache typeCache, int sizeCache, TypeStore typeStore) {
        this.size = sizeCache;
        switch (typeCache) {

            case LRU:
                this.cache = new LRUCache(size, typeStore);
                break;
            case LFU:
                this.cache = new LFUCache(size, typeStore);
                break;
        }
       
    }
    
    public Cache returnCache(){
        return this.cache;
    }
}
