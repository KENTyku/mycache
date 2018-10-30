/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.TypeStore;
import java.io.File;
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
public class LFUCache implements Cache, Serializable,FileOperationsForObjects {

    private final int size;
    private final TypeStore typeStore;

    private AlgorithmLFU lfu;

    public LFUCache(int cacheSize, TypeStore typeStore) {
        this.size = cacheSize;
        this.typeStore = typeStore;
        this.lfu = new AlgorithmLFU(cacheSize);

    }

    /**
     * Adding data to the cache and restore or save cashe into DataStore
     *
     */
    @Override
    public void addData(int key, String data) {
        switch (this.typeStore) {
            case HDD: {
                try {
                    lfu = (AlgorithmLFU) loadFromFile("cacheLfu.data");
                } catch (NullPointerException e) {
                    lfu = new AlgorithmLFU(this.size);
                }

                if (!lfu.findKey(key)) {
                    lfu.addCacheEntry(key, data);
                    saveToFile(lfu, "cacheLfu.data");
                }
            }
            break;
            case RAM: {
                if (!lfu.findKey(key)) {
                    lfu.addCacheEntry(key, data);
                }
            }
            break;
            default:
                throw new AssertionError(this.typeStore.name());
        }

    }

    /**
     * Getting data from the cache by key
     *
     */
    @Override
    public String getData(int key) {
        String temp = lfu.getCacheEntry(key);
        switch (this.typeStore) {
            case HDD:
                saveToFile(lfu, "cacheLfu.data");
                break;
            case RAM:
                break;
            default:
                throw new AssertionError(this.typeStore.name());

        }
        return temp;
    }

    /**
     * Reset cache
     */
    @Override
    public void resetStoreCache() {
        switch (this.typeStore) {
            case HDD:
                resetCacheHDD("cacheLfu.data");
                lfu.resetCache();
                break;
            case RAM:
                lfu.resetCache();
                break;
            default:
                throw new AssertionError(this.typeStore.name());

        }

    }

   
    @Override
    public HashMap<Integer, String> getCache() {
        switch (this.typeStore) {
            case HDD:
                lfu = (AlgorithmLFU) loadFromFile("cacheLfu.data");
                break;
            case RAM:
                break;
            default:
                throw new AssertionError(this.typeStore.name());
        }
        return lfu.getCache();
    }

    void resetCacheHDD(String fileName) {
        File file = new File(fileName);
        if (!file.delete()) {
            System.out.println("Файл " + fileName + " не был найден в корневой папке проекта");
        }
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
//            System.out.println("реже всего использовался элемент - " + key);
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
