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
import java.util.logging.Level;
import java.util.logging.Logger;

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

//    /**
//     * Set type of Data Store
//     *
//     * @param isFileStore Type of DataStore
//     */
//    @Override
//    public void setTypeDataStore(boolean isFileStore) {
//        this.isFileStore = isFileStore;
//    }

    /**
     * Adding data to the cache
     *
     * @param key unique key
     * @param data value
     */
    @Override
    public void addData(int key, String data) {
        FileInputStream fileForRead = null;
        ObjectInputStream inStreamObject = null;
        if (this.isFileStore) {
            try {
                fileForRead = new FileInputStream("cacheLru.data");
                inStreamObject = new ObjectInputStream(fileForRead);
                if (this.lru.size() == 0) {
                    this.lru = (AlgoritmLRU) inStreamObject.readObject();
//                    inStreamObject.close();
                }
            } catch (Exception e) {
                System.out.println("Ошибка загрузки кэша из файла cacheLru.data");
            } finally {
                try {
                    inStreamObject.close();
                    fileForRead.close();
                } catch (IOException|NullPointerException ex) {
                   System.out.println("Ошибка закрытия потока");
                }
            }
            lru.put(key, data);
            FileOutputStream fileForWrite = null;
            ObjectOutputStream outStreamObject = null;
            try {
                fileForWrite = new FileOutputStream("cacheLru.data");
                outStreamObject = new ObjectOutputStream(fileForWrite);
                outStreamObject.writeObject(this.lru);
                
            } catch (IOException e) {
                System.out.println("Ошибка выгрузки кэша в файл cacheLru.data");
            }
            finally{
                try {
                    outStreamObject.close();
                    fileForWrite.close();
                } catch (IOException|NullPointerException ex) {
                    Logger.getLogger(CacheLRU.class.getName()).log(Level.SEVERE, null, ex);
                }
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
