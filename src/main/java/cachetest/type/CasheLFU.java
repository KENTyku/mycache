/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class describing the creation of an LFU cache
 *
 * @author kentyku
 */
public class CasheLFU extends Cache implements Serializable {

    private AlgoritmLFU lfu;

    /**
     * Constructor of class CacheLRU
     *
     * @param maxEntries Cashe size
     */
    public CasheLFU(int maxEntries, boolean isFileStore) {
        this.isFileStore = false;
        this.size = maxEntries;
        this.isFileStore = isFileStore;
        this.lfu = new AlgoritmLFU(maxEntries);

    }

    /**
     * Adding data to the cache and restore or save cashe into DataStore
     *
     *
     * @param key unique key
     * @param data value
     */
    @Override
    public void addData(int key, String data) {

        if (this.isFileStore) {
            lfu = new AlgoritmLFU(this.size);
            if (lfu.getCache().isEmpty()) {
                lfu = (AlgoritmLFU) Cache.loadFromFile("cacheLfu.data");
            }
            lfu.addCacheEntry(key, data);
            Cache.saveToFile(lfu, "cacheLfu.data");
        } else {
            lfu.addCacheEntry(key, data);
        }
    }

    /**
     * Getting data from the cache by key
     *
     * @param key unique key
     * @return data from the cache by key
     */
    @Override
    public String getData(int key) {
        return lfu.getCacheEntry(key);
    }

    /**
     * Reset cache
     */
    @Override
    public void resetStoreCache() {
        lfu.resetCache();
    }

    /**
     * View values ​​in the cache
     *
     * @return values in the cache
     */
    @Override
    public HashMap<Integer, String> showCache() {

        return lfu.getCache();
    }

    /**
     * Class implementing LFU-cache
     *
     * @author kentyku
     */
    private class AlgoritmLFU implements Serializable {

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
            }
            return cachetemp;
        }

        public void resetCache() {
            cache.clear();
        }

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
}
