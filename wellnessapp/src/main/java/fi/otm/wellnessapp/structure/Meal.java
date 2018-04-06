/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author vili
 */
public class Meal implements Comparable {
    private final int userId;
    private Date time;
    private int mealId;
    
    private HashMap<FoodItem, Double> foodItems;
    private HashMap<NutritionalComponent, Double> totalNutritionalValues;
    
    public Meal(Date time, int userId) {
        this.userId = userId;
        this.time = time;
        this.totalNutritionalValues = null;
        this.foodItems = new HashMap<>();
    }
    public void setMealId(int id) {
        this.mealId = id;
    }
    
    public int getMealId() {
        return this.mealId;
    }
    
    public int getUserId() {
        return this.userId;
    }
     
    public void addFoodItem(FoodItem fi, double amount) {
        Double a = amount;
        this.foodItems.put(fi, a);
    }
    
    public HashMap<NutritionalComponent, Double> getTotalNutritionalValues () {
        if (this.totalNutritionalValues == null) {
            this.buildTotalNutritionalValues();
        }
        return this.totalNutritionalValues;
    }
    
    
    /*
       Builds new totalNutritionalValues HashMap from
        all of the fooditems in the meal.
    */
    private void buildTotalNutritionalValues() {
        this.totalNutritionalValues = new HashMap<>();
        this.foodItems.keySet().stream().forEach(k -> {
            /*
                for each item nutritional values are added to
                the hashmap
            */
            Double amountOfFood = this.foodItems.get(k);
            HashMap nutritionalValue = k.getContents();
            nutritionalValue.keySet().stream().forEach(r -> {
                /*
                    for each nutritional value in food item
                    the nutritional values are
                    calculated to the total hashmap
                */
                Double amountOfNutrient = amountOfFood *
                        ((Double)nutritionalValue.get(r))/100;
                Double old = this.totalNutritionalValues.put((NutritionalComponent)r, amountOfNutrient);
                if(old != null) {
                    Double val = old + amountOfNutrient;
                    this.totalNutritionalValues.replace((NutritionalComponent) r , val);
                }          
            });
        });
        
    }
    public Date getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Object o) {
        Meal m = (Meal)o;
        return this.time.compareTo(m.getTime());
    }
    
    
}
