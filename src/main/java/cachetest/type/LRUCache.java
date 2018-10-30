/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.StoreType;
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

    LRUCache(int size, StoreType typeStore) {
        this.size = size;
        this.cachelru = new CollectionForCacheLRU(size);
        this.typeStore = typeStore;
    }

    /**
     * Adding data to the cache
     *
     * @param key unique key
     * @param data value
     */
    @Override
    public void addData(int key, String data) {
        cachelru.put(key, data);
        if (HDD) {
            saveToFile(cachelru, "cacheLru.data");
        }
//        switch (this.typeStore) {
//            case HDD: {
//                if (cachelru.isEmpty()) {
//                    try {
//                        cachelru = (CollectionForCacheLRU) loadFromFile("cacheLru.data");
//                    } catch (NullPointerException e) {
//                        cachelru = new CollectionForCacheLRU(size);
//                    }
//                }
//
//                cachelru.put(key, data);
//                
//            }
//            break;
//            case RAM:
//                
//                break;
//            default:
//                throw new AssertionError(this.typeStore.name());
//
//        }
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
        if (HDD) {
            saveToFile(cachelru, "cacheLru.data");
        }
        
//        switch (this.typeStore) {
//            case HDD:
//                saveToFile(cachelru, "cacheLru.data");
//                break;
//            case RAM:
//                break;
//            default:
//                throw new AssertionError(this.typeStore.name());
//        }
        return data;
    }

    /**
     * Reset cache
     */
    @Override
    public void resetStoreCache() {
        switch (this.typeStore) {
            case HDD:
                resetCacheHDD("cacheLru.data");
                cachelru.clear();
                break;
            case RAM:
                cachelru.clear();
                break;
            default:
                throw new AssertionError(this.typeStore.name());
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

    void resetCacheHDD(String fileName) {
        File file = new File(fileName);
        if (!file.delete()) {
            System.out.println("Файл " + fileName + " не был найден в корневой папке проекта");
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
