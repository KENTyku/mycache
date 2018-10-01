/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.type.algoritm.AlgoritmLRU;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;

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
    public CacheLRU(int maxEntries) {
        this.size = maxEntries;
        this.lru = new AlgoritmLRU(size);
        this.isFileStore = false;
    }

    /**
     * Set type of Data Store
     *
     * @param isFileStore Type of DataStore
     */
    @Override
    public void setTypeDataStore(boolean isFileStore) {
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
            try {
                FileInputStream fileForRead = new FileInputStream("cacheLru.data");
                ObjectInputStream inStreamObject = new ObjectInputStream(fileForRead);
                if (this.lru.size() == 0) {
                    this.lru = (AlgoritmLRU) inStreamObject.readObject();
                    inStreamObject.close();
                }
            } catch (Exception e) {
                System.out.println("Ошибка загрузки кэша из файла cacheLru.data");
            }
            lru.put(key, data);
            try {
                FileOutputStream fileForWrite = new FileOutputStream("cacheLru.data");
                ObjectOutputStream outStreamObject = new ObjectOutputStream(fileForWrite);
                outStreamObject.writeObject(this.lru);
                outStreamObject.close();
            } catch (IOException e) {
                System.out.println("Ошибка выгрузки кэша в файл cacheLru.data");
            }
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

}
