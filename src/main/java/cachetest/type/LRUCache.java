/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.StoreType;
import static cachetest.StoreType.HDD;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class describing the creation of an LRU cache
 *
 * @author kentyku
 */
public class LRUCache extends Cache implements Serializable {

    private CollectionForCacheLRU cachelru;

    LRUCache(int size, StoreType storeType) {
        super(size, storeType);
//        this.size = size;
//        this.storeType = storeType;

        if (this.storeType == HDD) {
            try {
                this.cachelru = (CollectionForCacheLRU) loadFromFile("cacheLru.data");
                if (this.cachelru == null) {
                    this.cachelru = new CollectionForCacheLRU(size);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Некорректный файл кеш."
                        + " Файл будет создан заново.");
                this.cachelru = new CollectionForCacheLRU(size);
                saveToFile(this.cachelru, "cacheLru.data");
            }
        } else {
            this.cachelru = new CollectionForCacheLRU(size);
        }
        System.out.println(storeType);
    }

    /**
     * Adding data to the cache
     *
     * @param key unique key
     * @param data value
     */
    @Override
    public void addData(int key, String data) {
        this.cachelru.put(key, data);
        if (storeType == HDD) {
            saveToFile(cachelru, "cacheLru.data");
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
        String data = cachelru.get(key);
        if (this.storeType == HDD) {
            saveToFile(cachelru, "cacheLru.data");
        }
        return data;
    }

    /**
     * Reset cache
     */
    @Override
    public void resetStoreCache() {
        if (this.storeType == HDD) {
            resetCacheHDD("cacheLru.data");
            cachelru.clear();
        } else {
            cachelru.clear();
        }
    }

    /**
     * View values ​​in the cache
     *
     * @return Map of etems cache
     */
    @Override
    public LinkedHashMap<Integer, String> getCache() {
        return cachelru;
    }

    void resetCacheHDD(String fileName
    ) {
        File file = new File(fileName);
        if (!file.delete()) {
            System.out.println("Файл " + fileName 
                    + " не был найден в корневой папке проекта");
        }
    }

    /**
     * Internal Class for
     */
    private class CollectionForCacheLRU extends LinkedHashMap<Integer, String> {

        private int size;

        /**
         * Конструктор создаем как родительсткий с заданными значениями
         * Начальную емкость отображения выбираем в 2 раза больше максимума
         * хранимых в кэше значений. Этим обеспечиваем быструю работу с
         * отображением и невозможность увеличения размера отображения при
         * добавлении в него элементов.
         *
         * @param size Размер кэша
         */
        public CollectionForCacheLRU(int size) {
            super(size * 2, 0.75f, true);//как конструктор родителя 
            this.size = size;
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
            return size;
        }
    }
}
