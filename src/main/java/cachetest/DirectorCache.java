/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachetest;

/**
 *
 * @author jury
 */
public class DirectorCache {
    
   void constructCacheHDD(Builder builder){
       builder.createCache(5, TypeStore.HDD);
       
   }
   void constructCacheRAM(Builder builder){
       builder.createCache(5, TypeStore.RAM);
     
  }
  
//    Director()
//        
//            void buildCache
//        Builder 
//        builder.createCache(5, TypeStore.HDD);
//        builder.
//    }
    
}
