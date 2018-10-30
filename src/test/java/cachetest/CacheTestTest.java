/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

import cachetest.type.Cache;
import java.io.File;
import static org.junit.Assert.*;

/**
 *
 * @author jury
 */
public class CacheTestTest {

    public CacheTestTest() {
    }

    @org.junit.Test
    public void testCacheLRURAM() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        
        System.out.println("Чистим папку файлов кеша, если она не пустая");
        CacheBuilder cb = new CacheBuilder(TypeCache.LRU, 5, TypeStore.HDD);
        Cache cache = cb.returnCache();
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLru.data")).exists());
        System.out.println("Проверяем работу кеша");
        cb = new CacheBuilder(TypeCache.LRU, 5, TypeStore.RAM);
        cache = cb.returnCache();
        cache = fillCacheLRU(cache);
        Integer[] actuals = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] expecteds = {1, 2, 5, 6, 7};
        assertArrayEquals(actuals, expecteds);
        System.out.println("Проверяем что файл кеша не создается");
        assertFalse((new File("cacheLru.data")).exists());
    }

    @org.junit.Test
    public void testCacheLRUHDD() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        
        System.out.println("Чистим папку файлов кеша, если она не пустая");
        CacheBuilder cb = new CacheBuilder(TypeCache.LRU, 5, TypeStore.HDD);
        Cache cache = cb.returnCache();
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLru.data")).exists());
        System.out.println("Заполняем кеш");
        cache = fillCacheLRU(cache);
        Integer[] expecteds = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {1, 2, 5, 6, 7};
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
        assertTrue((new File("cacheLru.data")).exists());

        System.out.println("Чистим кеш");
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLru.data")).exists());
        System.out.println("Заполняем кеш");
        cache.addData(8, "Киев");
        expecteds = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        actuals = new Integer[1];
        actuals[0] = 8;
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
    }

    @org.junit.Test
    public void testCacheLFURAM() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        
        System.out.println("Чистим папку файлов кеша, если она не пустая");
        CacheBuilder cb = new CacheBuilder(TypeCache.LFU, 5, TypeStore.HDD);
        Cache cache = cb.returnCache();
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLfu.data")).exists());
        System.out.println("Заполняем кеш");
        cb = new CacheBuilder(TypeCache.LFU, 5, TypeStore.RAM);
        cache = cb.returnCache();
        cache = fillCacheLFU(cache);
        Integer[] expecteds = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {3, 4, 5, 6, 7};
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
        System.out.println("Проверяем что файл кеша не создается");
        assertFalse((new File("cacheLfu.data")).exists());
    }

    @org.junit.Test
    public void testCacheLFUHDD() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        System.out.println("Чистим папку файлов кеша, если она не пустая");
        CacheBuilder cb = new CacheBuilder(TypeCache.LFU, 5, TypeStore.HDD);
        Cache cache = cb.returnCache();
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLfu.data")).exists());
        System.out.println("Заполняем кеш");
        cache = fillCacheLFU(cache);
        Integer[] expecteds = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {3, 4, 5, 6, 7};
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
        System.out.println("Проверяем что файл кеша создается");
        assertTrue((new File("cacheLfu.data")).exists());
        System.out.println("Удаляем кэш");
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLfu.data")).exists());
        System.out.println("Заполняем кеш");
        cache.addData(8, "Киев");
        expecteds = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        actuals = new Integer[1];
        actuals[0] = 8;
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);

    }

    public Cache fillCacheLFU(Cache cache) {
        cache.addData(1, "Ижевск");
        cache.addData(2, "Лондон");
        cache.addData(3, "Венеция");
        cache.addData(4, "Берлин");
        cache.addData(5, "Вашингтон");
        cache.getData(1);
        cache.getData(2);
        cache.getData(2);
        cache.getData(3);
        cache.getData(3);
        cache.getData(4);
        cache.getData(4);
        cache.getData(5);
        cache.getData(5);
        cache.getData(5);
        cache.addData(6, "Токио");
        cache.getData(6);
        cache.getData(6);
        cache.getData(6);
        cache.addData(7, "Париж");
        return cache;
    }

    public Cache fillCacheLRU(Cache cache) {
        cache.addData(1, "Ижевск");
        cache.addData(2, "Лондон");
        cache.addData(3, "Венеция");
        cache.addData(4, "Берлин");
        cache.addData(5, "Вашингтон");
        cache.getData(1);
        cache.getData(2);
        cache.addData(6, "Токио");
        cache.addData(7, "Париж");
        return cache;
    }
}
