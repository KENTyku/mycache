/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type.algoritm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class implementing LFU-cache
 *
 * @author kentyku
 */
public class AlgoritmLFU implements Serializable {

    private int maxEntries;
    private HashMap<Integer, CacheEntryLFU> cache = new HashMap<Integer, CacheEntryLFU>();

    /**
     * Constructor
     *
     * @param maxEntries It is size cache.
     */
    public AlgoritmLFU(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * Adding data to cache
     *
     * @param key for etem cache
     * @param data for etem cache
     */
    public void addCacheEntry(int key, String data) {
        if (!isFull()) {
            CacheEntryLFU temp1 = new CacheEntryLFU();
            temp1.setData(data);
            temp1.setFrequency(0);

            cache.put(key, temp1);
        } else {
            int entryKeyToBeRemoved = getLFUKey();
            cache.remove(entryKeyToBeRemoved);

            CacheEntryLFU temp = new CacheEntryLFU();
            temp.setData(data);
            temp.setFrequency(0);

            cache.put(key, temp);
        }
    }

    /**
     * Returns the index of the least frequently used value
     *
     * @return
     */
    private int getLFUKey() {
        int key = 0;
        int minFreq = Integer.MAX_VALUE;

        for (Map.Entry<Integer, CacheEntryLFU> entry : cache.entrySet()) {
            if (minFreq > entry.getValue().getFrequency()) {
                key = entry.getKey();
                minFreq = entry.getValue().getFrequency();
            }
        }

        return key;
    }

    /**
     * Returns data from cache
     *
     * @param key for etem chache
     * @return entry for cache with frequency marker
     */
    public String getCacheEntry(int key) {
        if (cache.containsKey(key)) // cache hit
        {
            CacheEntryLFU temp = cache.get(key);
            temp.setFrequency(temp.getFrequency() + 1);
            cache.put(key, temp);
            return temp.getData();
        }
        return null; // cache miss
    }

    /**
     * Check whether the cache is full or not
     *
     * @return
     */
    private boolean isFull() {
        if (cache.size() == maxEntries) {
            return true;
        }

        return false;
    }

    /**
     * @return the cache
     */
    public HashMap<Integer, String> getCache() {
        HashMap<Integer, String> cachetemp = new HashMap<>();
        for (Map.Entry<Integer, CacheEntryLFU> entry : cache.entrySet()) {
            cachetemp.put(entry.getKey(), entry.getValue().getData());
//            System.out.println(entry.getValue().getData());
        }
        return cachetemp;
    }
    
    public void resetCache(){
        cache.clear();        
    }

//    /**
//     * @param aCache the cache to set
//     */
//    public void setCache(HashMap<Integer, CacheEntryLFU> aCache) {
//        cache = aCache;
//    }

    private class CacheEntryLFU implements Serializable {

        private String data;
        private int frequency;

        /**
         * Defaul constructor
         */
        CacheEntryLFU() {
        }

        /**
         * Get value for entry cache
         *
         * @return value for entry cache
         */
        public String getData() {
            return data;
        }

        /**
         * Set value for entry cache
         *
         * @param data value for entry cache
         */
        public void setData(String data) {
            this.data = data;
        }

        /**
         * Get frequency of entry cache
         *
         * @return frequency of entry cache
         */
        public int getFrequency() {
            return frequency;
        }

        /**
         * Set frequency entry cache
         *
         * @param frequency of entry cache
         */
        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

    }

}
