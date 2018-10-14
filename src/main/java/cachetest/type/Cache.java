/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Parent class. Describes how to create a cache
 *
 * @author kentyku
 */
abstract public class Cache {

    protected String type;//type of cache
    protected int key;
    protected String data;
    protected int size;//size of cache
    protected boolean isFileStore;//type Store
//    protected String typeDataStore;
//    Cache(String type, int size, String typeDataStore){
//        this.type=type;
//        this.size=size;
//        this.typeDataStore=typeDataStore;
//    }

//    abstract public void setTypeDataStore(boolean isFileStore);

    abstract public void addData(int key, String data);

    abstract public String getData(int key);

    abstract public void resetStoreCache();

    abstract public HashMap<Integer, String> showCache();

}
