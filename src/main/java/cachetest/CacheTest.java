/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest;

import static cachetest.TypeCache.*;
import static cachetest.TypeStore.*;

/**
 *
 * @author kentyku
 */
public class CacheTest {

    /**
     * Use for test.
     *
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {//assert-ы
        System.out.println("Тест LRU");
        ClientCache cacheLRU = new ClientCache(LRU, 5, RAM);
        System.out.println("Очистка кеша");
        cacheLRU.resetCash();
        System.out.println("Добавляем данные");
        cacheLRU.addData(1, "Ижевск");
        cacheLRU.addData(2, "Лондон");
        cacheLRU.addData(3, "Венеция");
        cacheLRU.addData(4, "Берлин");
        cacheLRU.addData(5, "Вашингтон");
        cacheLRU.getData(1);
        cacheLRU.getData(2);
        cacheLRU.addData(6, "Токио");
        cacheLRU.addData(7, "Париж");
        cacheLRU.showData();

        System.out.println("Очистка кеша");
        cacheLRU.resetCash();
        System.out.println("Добавляем данные");
        cacheLRU.addData(8, "Киев");
        cacheLRU.showData();

        System.out.println();
        System.out.println("Тест LFU");
        ClientCache cacheLFU = new ClientCache(LFU, 5, HDD);
        System.out.println("Очистка кеша");
        cacheLFU.resetCash();
        //        cacheLFU.showData();
        System.out.println("Добавляем данные");
        cacheLFU.addData(1, "Ижевск");
        cacheLFU.addData(2, "Лондон");
        cacheLFU.addData(3, "Венеция");
        cacheLFU.addData(4, "Берлин");
        cacheLFU.addData(5, "Вашингтон");
        cacheLFU.getData(1);
        cacheLFU.getData(2);
        System.out.println(cacheLFU.getData(1));
        System.out.println(cacheLFU.getData(2));
        cacheLFU.addData(6, "Токио");
        cacheLFU.addData(7, "Париж");
        cacheLFU.showData();

        System.out.println("Очистка кеша");
        cacheLFU.resetCash();
        System.out.println("Добавляем данные");
        cacheLFU.addData(8, "Киев");
        cacheLFU.showData();
    }

}
