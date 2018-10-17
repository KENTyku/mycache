/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

import java.io.File;
import static org.junit.Assert.*;

/**
 *
 * @author jury
 */
public class ClientCacheTest {

    public ClientCacheTest() {
    }

    public ClientCache workingWithCacheLFU(ClientCache cache) {
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

    public ClientCache workingWithCacheLRU(ClientCache cache) {
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

    @org.junit.Test
    public void testCacheLRURAM() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        ClientCache cache = new ClientCache(TypeCache.LRU, 5, TypeStore.HDD);
        cache.resetCash();

        cache = new ClientCache(TypeCache.LRU, 5, TypeStore.RAM);
        assertFalse((new File("cacheLru.data")).exists());
        cache = workingWithCacheLRU(cache);
        Integer[] expecteds = cache.getAllCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {1, 2, 5, 6, 7};

        assertArrayEquals(expecteds, actuals);
        assertFalse((new File("cacheLru.data")).exists());
    }

    @org.junit.Test
    public void testCacheLRUHDD() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));

        ClientCache cache = new ClientCache(TypeCache.LRU, 5, TypeStore.HDD);
        cache.resetCash();
        assertFalse((new File("cacheLru.data")).exists());
        cache = workingWithCacheLRU(cache);
        Integer[] expecteds = cache.getAllCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {1, 2, 5, 6, 7};
        assertArrayEquals(expecteds, actuals);
        assertTrue((new File("cacheLru.data")).exists());

        System.out.println("Удаляем кэш");
        cache.resetCash();
        assertFalse((new File("cacheLru.data")).exists());
        cache.addData(8, "Киев");
        expecteds = cache.getAllCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        actuals = new Integer[1];
        actuals[0] = 8;
        assertArrayEquals(expecteds, actuals);
    }

    @org.junit.Test
    public void testCacheLFURAM() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        ClientCache cache = new ClientCache(TypeCache.LFU, 5, TypeStore.HDD);
        cache.resetCash();

        cache = new ClientCache(TypeCache.LFU, 5, TypeStore.RAM);
        assertFalse((new File("cacheLfu.data")).exists());
        cache = workingWithCacheLFU(cache);
        Integer[] expecteds = cache.getAllCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {3, 4, 5, 6, 7};

        assertArrayEquals(expecteds, actuals);
        assertFalse((new File("cacheLfu.data")).exists());
    }

    @org.junit.Test
    public void testCacheLFUHDD() {
        class LocalClassForGetNameMetod {
        }
        System.out.println("");
        System.out.println("Тест "
                + (LocalClassForGetNameMetod.class.getEnclosingMethod().getName()));
        ClientCache cache = new ClientCache(TypeCache.LFU, 5, TypeStore.HDD);
        cache.resetCash();
        assertFalse((new File("cacheLfu.data")).exists());
        cache = workingWithCacheLFU(cache);
        Integer[] expecteds = cache.getAllCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        Integer[] actuals = {3, 4, 5, 6, 7};

        assertArrayEquals(expecteds, actuals);
        assertTrue((new File("cacheLfu.data")).exists());

        System.out.println("Удаляем кэш");
        cache.resetCash();
        assertFalse((new File("cacheLfu.data")).exists());
        cache.addData(8, "Киев");
        expecteds = cache.getAllCache()
                .keySet()
                .stream()
                .sorted()
                .toArray(Integer[]::new);

        actuals = new Integer[1];
        actuals[0] = 8;
        assertArrayEquals(expecteds, actuals);

    }
}