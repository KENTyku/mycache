/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.StoreType;
import static cachetest.StoreType.HDD;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class describing the creation of an LFU cache
 *
 * @author kentyku
 */
public class LFUCache extends Cache implements Serializable {

    private AlgorithmLFU lfu;

    LFUCache(int size, StoreType storeType) {
        super(size, storeType);
        if (this.storeType == HDD) {
            try {
                lfu = (AlgorithmLFU) loadFromFile("cacheLfu.data");
                if (lfu == null) {
                    lfu = new AlgorithmLFU(this.size);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Некорректный файл кеш. Файл будет создан заново." + e.getMessage());
                lfu = new AlgorithmLFU(this.size);
                saveToFile(lfu, "cacheLfu.data");

            }
        } else {
            lfu = new AlgorithmLFU(this.size);
        }
    }

    /**
     * Adding data to the cache and restore or save cashe into DataStore
     *
     */
    @Override
    public void addData(int key, String data) {
        lfu.addCacheEntry(key, data);
        if (this.storeType == HDD) {
            saveToFile(lfu, "cacheLfu.data");
        }
    }

    /**
     * Getting data from the cache by key
     *
     */
    @Override
    public String getData(int key) {
        String temp = lfu.getCacheEntry(key);
        if (this.storeType == HDD) {
            saveToFile(lfu, "cacheLfu.data");
        }
        return temp;
    }

    /**
     * Reset cache
     */
    @Override
    public void resetStoreCache() {
        if (this.storeType == HDD) {
            deleteFile("cacheLfu.data");
        }
        lfu.resetCache();
    }

    @Override
    public HashMap<Integer, String> getCache() {
        if (this.storeType == HDD) {
            try {
                lfu = (AlgorithmLFU) loadFromFile("cacheLfu.data");
            } catch (ClassNotFoundException ex) {
                System.out.println("Ошибка файла кеш. "
                        + "Файл будет создан заново.");
                lfu = new AlgorithmLFU(this.size);
                saveToFile(this.lfu, "cacheLfu.data");
            }
        }
        return lfu.getCache();
    }

    /**
     * Class implementing LFU-Algorithm
     *
     * @author kentyku
     */
    private class AlgorithmLFU implements Serializable {

        private final int maxEntries;
        private final HashMap<Integer, CacheEntryLFU> cache;

        public AlgorithmLFU(int size) {
            this.cache = new HashMap<>();
            this.maxEntries = size;
        }

        public boolean findKey(int key) {
            return cache.containsKey(key);
        }

        /**
         * Adding data to cache
         *
         * @param key for etem cache
         * @param data for etem cache
         */
        public void addCacheEntry(int key, String data) {
            if (isFull()) {
                int entryKeyToBeRemoved = getLFUKey();
                cache.remove(entryKeyToBeRemoved);
            }
            CacheEntryLFU entry = new CacheEntryLFU();
            entry.setData(data);
            entry.setFrequency(0);
            if (this.cache.putIfAbsent(key, entry) != null) {
                System.out.println("Значение с данным ключом " + key + " уже присутствует, "
                        + "поэтому не может быть добавлено");
            }
        }

        /**
         * Check whether the cache is full or not
         *
         * @return
         */
        private boolean isFull() {
            return cache.size() == maxEntries;
        }

        /**
         * Returns the index of the least frequently used value
         *
         * @return
         */
        private int getLFUKey() {
            Comparator<Entry<Integer, CacheEntryLFU>> comparator
                    = (Entry<Integer, CacheEntryLFU> e1, Entry<Integer, CacheEntryLFU> e2)
                    -> ((Integer) e1.getValue().getFrequency()).compareTo((Integer) e2.getValue()
                            .getFrequency());
            int key;
            key = cache
                    .entrySet()
                    .stream()
                    .min(comparator)
                    .get()
                    .getKey();
            return key;
        }

        /**
         * Returns data from cache
         *
         * @param key for etem chache
         * @return entry for cache with frequency marker
         */
        public String getCacheEntry(int key) {
            if (cache.containsKey(key)) {
                CacheEntryLFU temp = cache.get(key);
                temp.setFrequency(temp.getFrequency() + 1);
                cache.put(key, temp);
                return temp.getData();
            }
            return null;
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

        /**
         * Class for entry of CacheLFU
         */
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
