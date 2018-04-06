/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.util.HashMap;

/**
 *
 * @author vili
 */
public class FoodItem implements Comparable{
    private final int id;
    private final String name;
    private final String language;
    private HashMap<NutritionalComponent, Double> contents;
    
    public FoodItem(int id, String name, String language) {
        this.id = id;
        this.name = name;
        contents = new HashMap<>();
        this.language = language;
    }
    
    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void addContents( double amount, NutritionalComponent nc) {
        Double a = amount;
        this.contents.put(nc,a);
    }
    public HashMap<NutritionalComponent, Double> getContents() {
        return this.contents;
    }
    @Override
    public int hashCode() {
        return this.id;
    }
    
    @Override
    public boolean equals(Object o) {
      if(o.getClass().equals(this.getClass())) {
            FoodItem fo = (FoodItem)o;    
            return this.id == fo.getId();
      }else {
          return false;
      }
    }
    
    @Override
    public int compareTo(Object o) {
        if(o.getClass().equals(this.getClass())) {
            FoodItem fo = (FoodItem)o;
            if( this.id > fo.getId()) {
                return 1;
            } else if(this.id < fo.getId()) {
                return -1;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
