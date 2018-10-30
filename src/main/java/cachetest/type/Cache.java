/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package cachetest.type;

import java.util.HashMap;


public interface Cache {

    public abstract void addData(int key, String data);

    public abstract String getData(int key);

    public abstract void resetStoreCache();

    public abstract HashMap<Integer, String> getCache();   
}
