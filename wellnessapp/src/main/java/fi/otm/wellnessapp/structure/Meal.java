/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringJoiner;

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
        this.buildTotalNutritionalValues();
    }

    public void removeFoodItem(FoodItem fi) {
        this.foodItems.remove(fi);
    }

    public HashMap<NutritionalComponent, Double> getTotalNutritionalValues() {
        if (this.totalNutritionalValues == null) {
            this.buildTotalNutritionalValues();
        }
        return this.totalNutritionalValues;
    }

    public HashMap<FoodItem, Double> getFoodItems() {
        return this.foodItems;
    }

    /*
       Builds new totalNutritionalValues HashMap from
        all of the fooditems in the meal.
     */
    private void buildTotalNutritionalValues() {
        this.totalNutritionalValues = new HashMap<>();
        this.foodItems.keySet().stream().forEach(k -> {
            Double amountOfFood = this.foodItems.get(k);
            HashMap<NutritionalComponent, Double> nutritionalValue = k.getContents();
            nutritionalValue.keySet().stream().forEach(r -> {
                Double amountOfNutrient = amountOfFood
                        * (nutritionalValue.get(r)) / 100;
                Double old = this.totalNutritionalValues.put(r, amountOfNutrient);
                if (old != null) {
                    Double val = old + amountOfNutrient;
                    this.totalNutritionalValues.replace(r, val);
                }
            });
        });
    }

    public Date getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Object o) {
        Meal m = (Meal) o;
        return this.time.compareTo(m.getTime());
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String dateString = df.format(this.time);
        NutritionalComponentStructure ncs = NutritionalComponentStructure.getNutrititonalComponentStructure();
        Double energy = this.getTotalNutritionalValues().get(ncs.getNutCompByName("ENERC"));
        energy = energy / 4.1868;
        String s = dateString + " : " + String.format("%.2f", energy) + "kcal";
        return s;
    }

    public String info() {
        StringJoiner sj = new StringJoiner("\n");
        this.foodItems.forEach((k, v) -> {
            String pair = k.getName().split(",")[0] + " määrä: " + String.format("%.2f", v) + "g";
            sj.add(pair);
        });
        return sj.toString();
    }

}
