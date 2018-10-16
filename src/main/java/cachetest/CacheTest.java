/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest;

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
    public static void main(String[] args) throws Exception {
        System.out.println("Тест LRU");
        CacheBuilder cacheLRU = new CacheBuilder("LRU1", 5, true);
        cacheLRU.addData(1, "Ижевск");
        cacheLRU.addData(2, "Лондон");
        cacheLRU.addData(3, "Венеция");
        cacheLRU.addData(4, "Берлин");
        cacheLRU.addData(5, "Вашингтон");
        cacheLRU.getData(3);
        cacheLRU.addData(6, "Токио");
        cacheLRU.addData(7, "Париж");
        cacheLRU.getData(4);
        cacheLRU.getData(1);
        cacheLRU.showData();

        System.out.println("Очистка из оперативной памяти");
        cacheLRU.resetCash();
        cacheLRU.addData(8, "Киев");
        cacheLRU.showData();

        System.out.println();
        System.out.println("Тест LFU");
        CacheBuilder cacheLFU = new CacheBuilder("LFU", 5, true);
//        cacheLFU.showData();
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

        System.out.println("Очистка из оперативной памяти");
        cacheLFU.resetCash();
        cacheLFU.addData(8, "Киев");
        cacheLFU.showData();
    }

}
