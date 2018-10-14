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
abstract public class Cache {

    protected String type;//type of cache
    protected int key;
    protected String data;
    protected int size;//size of cache
    protected boolean isFileStore;//type Store

    abstract public void addData(int key, String data);

    abstract public String getData(int key);

    abstract public void resetStoreCache();

    abstract public HashMap<Integer, String> showCache();
    
    static protected Object loadFromFile(String fileName) {
        Object obj = null;
        FileInputStream fileForRead = null;
        ObjectInputStream inStreamObject = null;
        try {
            fileForRead = new FileInputStream(fileName);
            inStreamObject = new ObjectInputStream(fileForRead);
            obj = inStreamObject.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка загрузки кэша из файла cacheLru.data");
        } finally {
            try {
                inStreamObject.close();
                fileForRead.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Ошибка закрытия потока");
            }
        }
        return obj;
    }

    static protected void saveToFile(Object obj, String fileName) {
        FileOutputStream fileForWrite = null;
        ObjectOutputStream outStreamObject = null;
        try {
            fileForWrite = new FileOutputStream(fileName);
            outStreamObject = new ObjectOutputStream(fileForWrite);
            outStreamObject.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Ошибка выгрузки кэша в файл cacheLru.data");
        } finally {
            try {
                outStreamObject.close();
                fileForWrite.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println("Ошибка закрытия потока");
            }
        }
    }

}
