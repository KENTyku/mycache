/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

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
public abstract class Cache {

    protected String type;//type of cache
    protected int key;
    protected String data;
    protected int size;//size of cache
    protected boolean isFileStore;//type Store

    public abstract void addData(int key, String data);

    public abstract String getData(int key);

    public abstract void resetStoreCache();

    public abstract HashMap<Integer, String> showCache();

    /**
     * Load object from file
     *
     * @param fileName
     * @return
     */
    protected Object loadFromFile(String fileName) {
        Object obj = null;
        try (FileInputStream fileForRead = new FileInputStream(fileName);
                ObjectInputStream objIS = new ObjectInputStream(fileForRead)) {
            obj = objIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки кэша из файла cacheLru.data. "
                    + "Данный файл будет заново создан.");
        }
        return obj;
    }

    /**
     * Save obj to file
     * 
     * @param obj
     * @param fileName
     */
    protected void saveToFile(Object obj, String fileName) {
        try (FileOutputStream fileForWrite = new FileOutputStream(fileName);
                ObjectOutputStream objOS = new ObjectOutputStream(fileForWrite);) {
            objOS.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Ошибка выгрузки кэша в файл cacheLru.data. "
                    + "Данный файл будет заново создан.");
        }
    }

}
