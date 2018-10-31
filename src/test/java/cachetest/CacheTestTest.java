/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

import cachetest.type.CacheBuilder;
import cachetest.type.Cache;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jury
 */
public class CacheTestTest {

    Cache cache;

    public CacheTestTest() {
    }

    @Test
    public void testCacheLRURAM() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));

        Cache.deleteFile("cacheLfu.data");
        Cache.deleteFile("cacheLru.data");
        System.out.println("Заполняем кеш");
        cache = new CacheBuilder(CacheType.LRU, 5, StoreType.RAM).getCacheObject();
        cache = fillCache();
        Integer[] actuals = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);
        Integer[] expecteds = {2, 3, 4, 6, 7};
        System.out.println("Проверяем работу кеша");
        assertArrayEquals("Unexpected entries order", expecteds, actuals);
        System.out.println("Проверяем что файл кеша не создается");
        assertFalse((new File("cacheLru.data")).exists());
    }

    @Test
    public void testCacheLRUHDD() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));

        Cache.deleteFile("cacheLfu.data");
        Cache.deleteFile("cacheLru.data");
        System.out.println("Заполняем кеш");
        cache = new CacheBuilder(CacheType.LRU, 5, StoreType.HDD).getCacheObject();
        cache = fillCache();
        Integer[] actuals = cache.getCache().keySet().toArray(new Integer[5]);
        Integer[] expecteds = {2, 3, 4, 6, 7};
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
        assertTrue((new File("cacheLru.data")).exists());
        System.out.println("Чистим кеш");
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLru.data")).exists());
        System.out.println("Заполняем кеш");
        cache.addData(8, "Киев");
        actuals = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        expecteds = new Integer[1];
        expecteds[0] = 8;
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
        System.out.println("Удаляем файл кеша");
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLru.data")).exists());
    }

    @org.junit.Test
    public void testCacheLFURAM() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));

        Cache.deleteFile("cacheLfu.data");
        Cache.deleteFile("cacheLru.data");
        System.out.println("Заполняем кеш");
        cache = new CacheBuilder(CacheType.LFU, 5, StoreType.RAM).getCacheObject();
        cache = fillCache();
        Integer[] actuals = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] expecteds = {3, 4, 5, 6, 7};
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

        Cache.deleteFile("cacheLfu.data");
        Cache.deleteFile("cacheLru.data");
        cache = new CacheBuilder(CacheType.LFU, 5, StoreType.HDD).getCacheObject();
        System.out.println("Заполняем кеш");
        cache = fillCache();        
        Integer[] actuals = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] expecteds = {3, 4, 5, 6, 7};
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
        actuals = cache.getCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        expecteds = new Integer[1];
        expecteds[0] = 8;
        System.out.println("Проверяем работу кеша");
        assertArrayEquals(expecteds, actuals);
        System.out.println("Удаляем файл кеша");
        cache.resetStoreCache();
        System.out.println("Проверяем что почистилась папка");
        assertFalse((new File("cacheLfu.data")).exists());

    }

    public Cache fillCache() {
        cache.addData(1, "Ижевск");
        cache.addData(2, "Лондон");
        cache.addData(3, "Венеция");
        cache.addData(4, "Берлин");
        cache.addData(5, "Вашингтон");
        cache.getData(5);
        cache.getData(5);
        cache.getData(5);
        cache.getData(1);
        cache.getData(2);
        cache.getData(2);
        cache.getData(3);
        cache.getData(3);
        cache.getData(4);
        cache.getData(4);
        cache.addData(6, "Токио");
        cache.getData(6);
        cache.getData(6);
        cache.getData(6);
        cache.addData(7, "Париж");
        return cache;
    }

}
