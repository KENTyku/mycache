/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import cachetest.TypeStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Parent class. Describes how to create a cache
 *
 * @author kentyku
 */
public interface CacheBuilder {

    public abstract void addData(int key, String data);

    public abstract String getData(int key);

    public abstract void resetStoreCache();

    public abstract HashMap<Integer, String> getCache();

    /**
     * Load object from file
     *
     * @param fileName
     * @return
     */
    default Object loadFromFile(String fileName) throws NullPointerException {
        Object obj = null;
        try (FileInputStream fileForRead = new FileInputStream(fileName);
                ObjectInputStream objIS = new ObjectInputStream(fileForRead)) {
            obj = objIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки кэша из файла cacheLru.data. "
                    + "Кеш будет создан заново .");
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * Save obj to file
     *
     * @param obj
     * @param fileName
     */
    default void saveToFile(Object obj, String fileName) {
        try (FileOutputStream fileForWrite = new FileOutputStream(fileName);
                ObjectOutputStream objOS = new ObjectOutputStream(fileForWrite);) {
            objOS.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Ошибка выгрузки кэша в файл cacheLru.data. "
                    + "Убедитесь что HDD доступен для записи.");
        }
    }

    default void resetCacheHDD(String fileName) {
        File file = new File(fileName);
        if (!file.delete()) {
            System.out.println("Файл "+fileName+" не был найден в корневой папке проекта");
        }
    }

}
