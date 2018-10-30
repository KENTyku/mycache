/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest.type;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author jury
 */
public interface FileOperationsForObjects {
    
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
    
}
