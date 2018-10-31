/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.CacheType;
import cachetest.StoreType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public abstract class Cache implements Serializable{

    protected int size;
    protected StoreType storeType;
    protected CacheType cacheType;

    Cache(int size, StoreType storetype) {
        this.size = size;
        this.storeType = storetype;
    }

    public abstract void addData(int key, String data);

    public abstract String getData(int key);

    public abstract void resetStoreCache();

    public abstract HashMap<Integer, String> getCache();

    /**
     * Load object from file
     *
     * @param fileName
     * @return
     * @throws java.lang.ClassNotFoundException
     */
    protected final Object loadFromFile(String fileName) throws ClassNotFoundException {
        Object obj = null;
        try (FileInputStream fileForRead = new FileInputStream(fileName);
                ObjectInputStream objIS = new ObjectInputStream(fileForRead)) {
            obj = objIS.readObject();
        } catch (IOException  e) {
            System.out.println("Ошибка загрузки кэша из файла. "
                    + "Кеш будет создан заново .");
        }
        return obj;
    }

    /**
     * Save obj to file
     *
     * @param obj
     * @param fileName
     */
    protected final void saveToFile(Object obj, String fileName) {
        try (FileOutputStream fileForWrite = new FileOutputStream(fileName);
                ObjectOutputStream objOS = new ObjectOutputStream(fileForWrite);) {
            objOS.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Ошибка выгрузки кэша в файл cacheLru.data. "
                    + "Убедитесь что HDD доступен для записи.");
        }
    } 
    protected final void deleteFile(String fileName) {
        File file = new File(fileName);
        if (!file.delete()) {
            System.out.println("Файл " + fileName + " не был найден в корневой папке проекта");
        }
    }
}
