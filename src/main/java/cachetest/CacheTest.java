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
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        System.out.println("Тест LRU");
        CacheBuilder cacheLRU = new CacheBuilder("LRU1", 10,true);
//        cacheLRU.setTypeDataStore(true);
        cacheLRU.addData(1, "Ижевск");
        cacheLRU.addData(2, "Лондон");
        cacheLRU.addData(3, "Венеция");
        cacheLRU.addData(4, "Берлин");
        cacheLRU.addData(5, "Вашингтон");
        cacheLRU.getData(3);
        cacheLRU.addData(6, "Токио");
        cacheLRU.addData(7, "Париж");
        cacheLRU.showData();
        
        System.out.println("Очистка из оперативной памяти");
        cacheLRU.resetCash();
        cacheLRU.addData(8, "Киев");
        cacheLRU.showData();

        System.out.println();
        System.out.println("Тест LFU");
        CacheBuilder cacheLFU = new CacheBuilder("LFU", 10,true);
//        cacheLFU.setTypeDataStore(true);
        cacheLFU.addData(1, "Ижевск");
        cacheLFU.addData(2, "Лондон");
        cacheLFU.addData(3, "Венеция");
        cacheLFU.addData(4, "Берлин");
        cacheLFU.addData(5, "Вашингтон");
        System.out.println(cacheLFU.getData(3));
        cacheLFU.addData(6, "Токио");
        cacheLFU.addData(7, "Париж");       
        cacheLFU.showData();
        
        System.out.println("Очистка из оперативной памяти");
        cacheLFU.resetCash();
        cacheLFU.addData(8, "Киев");
        cacheLFU.showData();
    }

}
