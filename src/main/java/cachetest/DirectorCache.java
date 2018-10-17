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
    
   void constructCacheHDD(Builder builder, int size,TypeStore typeStore){
       builder.createCache(size, typeStore);
       
   }
   void constructCacheRAM(Builder builder){
       builder.createCache(5, TypeStore.RAM);
     
  }  

}
