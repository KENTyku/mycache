/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

import cachetest.type.CacheLFU;
import cachetest.type.CacheLRU;

/**
 *
 * @author jury
 */
public class LFUBuilder implements Builder {

    CacheLFU cache;

    @Override
    public void createCache(int size, TypeStore typeStore) {
        cache = new CacheLFU(size, typeStore);
    }

    public CacheLFU getCacheLFU() {

        return cache;
    }
}
