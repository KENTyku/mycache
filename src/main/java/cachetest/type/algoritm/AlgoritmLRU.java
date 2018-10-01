/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type.algoritm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class implementing LRU-cache
 *
 * @author kentyku
 */
public class AlgoritmLRU extends LinkedHashMap<Integer, String> {

    private int maxEntries;

    /**
     * Конструктор создаем как родительсткий с заданными значениями Начальную
     * емкость отображения выбираем в 2 раза больше максимума хранимых в кэше
     * значений. Этим обеспечиваем быструю работу с отображением и невозможность
     * увеличения размера отображения при добавлении в него элементов.
     *
     * @param maxEntries Размер кэша
     */
    public AlgoritmLRU(int maxEntries) {
        super(maxEntries * 2, 0.75f, true);//как конструктор родителя 
        this.maxEntries = maxEntries;
    }

    /**
     * метод вызывается при работе метода put() и удаляет самое старое значение
     * из отображения если возвращает истину(по умолчанию всегда возвращает
     * ложь). При переопределении добавлено условие, при котором метод
     * возвращает истину.
     *
     * @param eldest итератор 
     * @return is remove eldest entry
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > getMaxEntries();
    }

    /**
     * @return the maxEntries
     */
    public int getMaxEntries() {
        return maxEntries;
    }

    /**
     * @param maxEntries the maxEntries to set
     */
    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }

}
