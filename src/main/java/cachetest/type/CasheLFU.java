/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.type.algoritm.AlgoritmLFU;
import cachetest.type.algoritm.CacheEntryLFU;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
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
    public CasheLFU(int maxEntries) {
        this.isFileStore = false;
        this.size = maxEntries;
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
            try {
                lfu = new AlgoritmLFU(this.size);
                FileInputStream fileForRead = new FileInputStream("cacheLfu.data");
                ObjectInputStream inStreamObject = new ObjectInputStream(fileForRead);
                if (lfu.getCache().isEmpty()) {
                    lfu = (AlgoritmLFU) inStreamObject.readObject();
                    inStreamObject.close();
                }
            } catch (Exception e) {
                System.out.println("Ошибка загрузки кэша из файла cacheLfu.data");
            }

            lfu.addCacheEntry(key, data);
            try {
                FileOutputStream fileForWrite = new FileOutputStream("cacheLfu.data");
                ObjectOutputStream outStreamObject = new ObjectOutputStream(fileForWrite);
                outStreamObject.writeObject(lfu);
                outStreamObject.close();
            } catch (IOException e) {
                System.out.println("Ошибка выгрузки кэша в файл cacheLfu.data");
            }
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
        lfu.getCache().clear();
    }

    /**
     * View values ​​in the cache
     *
     * @return values in the cache
     */
    @Override
    public LinkedHashMap<Integer, String> showCache() {
        LinkedHashMap<Integer, String> cacheTemp = new LinkedHashMap<Integer, String>();
        for (Map.Entry<Integer, CacheEntryLFU> entry : lfu.getCache().entrySet()) {
            cacheTemp.put(entry.getKey(), entry.getValue().getData());
        }
        return cacheTemp;
    }

    /**
     * Set type of Data Store
     *
     * @param isFileStore true-use HDD. false-use RAM(is defaul).
     */
    @Override
    public void setTypeDataStore(boolean isFileStore) {
        this.isFileStore = isFileStore;
    }

}
