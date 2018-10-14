/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class describing the creation of an LRU cache
 *
 * @author kentyku
 */
public class CacheLRU extends Cache implements Serializable {

    private AlgoritmLRU lru;

    /**
     * Constructor of class CacheLRU
     *
     * @param maxEntries cache size
     */
    public CacheLRU(int maxEntries, boolean isFileStore) {
        this.size = maxEntries;
        this.lru = new AlgoritmLRU(size);
        this.isFileStore = isFileStore;
    }

    /**
     * Adding data to the cache
     *
     * @param key unique key
     * @param data value
     */
    @Override
    public void addData(int key, String data) {

        if (this.isFileStore) {
            if (this.lru.isEmpty()) {
                this.lru = (AlgoritmLRU) Cache.loadFromFile("cacheLru.data");
            }
            if (this.lru==null) this.lru = new AlgoritmLRU(size);
            lru.put(key, data);
            Cache.saveToFile(lru, "cacheLru.data");
        } else {
            lru.put(key, data);
        }
    }

    /**
     * Getting data from the cache by key
     *
     * @param key unique key
     * @return Value of cache with key
     */
    @Override
    public String getData(int key) {
        return lru.get(key);
    }

    /**
     * Reset cache
     */
    @Override
    public void resetStoreCache() {
        lru.clear();
    }

    /**
     * View values ​​in the cache
     *
     * @return Map of etems cache
     */
    @Override
    public LinkedHashMap<Integer, String> showCache() {
        return lru;
    }

    /**
     * Internal Class for
     */
    private class AlgoritmLRU extends LinkedHashMap<Integer, String> {

        private int maxEntries;

        /**
         * Конструктор создаем как родительсткий с заданными значениями
         * Начальную емкость отображения выбираем в 2 раза больше максимума
         * хранимых в кэше значений. Этим обеспечиваем быструю работу с
         * отображением и невозможность увеличения размера отображения при
         * добавлении в него элементов.
         *
         * @param maxEntries Размер кэша
         */
        public AlgoritmLRU(int maxEntries) {
            super(maxEntries * 2, 0.75f, true);//как конструктор родителя 
            this.maxEntries = maxEntries;
        }

        /**
         * метод вызывается при работе метода put() и удаляет самое старое
         * значение из отображения если возвращает истину(по умолчанию всегда
         * возвращает ложь). При переопределении добавлено условие, при котором
         * метод возвращает истину.
         *
         * @param eldest итератор
         * @return is remove eldest entry
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
            return size() > getMaxEntries();
        }

        /**
         * @return the maxEntries
         */
        public int getMaxEntries() {
            return maxEntries;
        }
    }
}
