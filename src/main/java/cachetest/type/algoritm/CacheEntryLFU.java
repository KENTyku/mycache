/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type.algoritm;

import java.io.Serializable;

/**
 * Describes the cache entry
 *
 * @author kentyku
 */
public class CacheEntryLFU implements Serializable {

    private String data;
    private int frequency;

    /**
     * Defaul constructor
     */
    CacheEntryLFU() {
    }

    /**
     * Get value for entry cache
     *
     * @return value for entry cache
     */
    public String getData() {
        return data;
    }

    /**
     * Set value for entry cache
     *
     * @param data value for entry cache
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Get frequency of entry cache
     *
     * @return frequency of entry cache
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Set frequency entry cache
     *
     * @param frequency of entry cache
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
