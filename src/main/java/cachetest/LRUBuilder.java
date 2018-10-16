/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

import cachetest.type.CacheLRU;

/**
 *
 * @author jury
 */
public class LRUBuilder implements Builder {

    CacheLRU cache;

    @Override
    public void createCache(int size, TypeStore typeStore) {
       cache = new CacheLRU(size, typeStore);
        
    }

    public CacheLRU getCacheLRU() {

        return cache;
    }
}
