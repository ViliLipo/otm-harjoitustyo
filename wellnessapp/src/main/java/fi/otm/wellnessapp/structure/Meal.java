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
 * Class for managing meals
 *
 * @author vili
 *
 */
public class Meal implements Comparable<Meal> {

    private final int userId;
    private Date time;
    private int mealId = -1;

    private HashMap<FoodItem, Double> foodItems;
    private HashMap<NutritionalComponent, Double> totalNutritionalValues;

    /**
     *
     * @param time, time when the meal happened.
     * @param userId id from the user that had this meal.
     */
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
        this.totalNutritionalValues = null;
    }

    public void removeFoodItem(FoodItem fi) {
        this.foodItems.remove(fi);
    }

    /**
     *
     * @return HashMap of the combined nutritional values of all food items in
     * this Meal
     */
    public HashMap<NutritionalComponent, Double> getTotalNutritionalValues() {
        if (this.totalNutritionalValues == null) {
            this.buildTotalNutritionalValues();
        }
        return this.totalNutritionalValues;
    }

    public HashMap<FoodItem, Double> getFoodItems() {
        return this.foodItems;
    }

    /**
     * Builds new totalNutritionalValues HashMap from all of the fooditems in
     * the meal.
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
    public int hashCode() {
        return this.time.hashCode() + this.userId;
    }

    @Override
    public int compareTo(Meal o) {
        if (o.getClass() == this.getClass()) {
            Meal m = (Meal) o;
            return this.time.compareTo(m.getTime());
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            return (this.getClass() == o.getClass()
                    && this.hashCode() == o.hashCode());
        } catch (NullPointerException ex) {
            return false;
        }

    }

    /**
     *
     * @return String representation of this meal.
     */
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String dateString = df.format(this.time);
        NutritionalComponentStructure ncs = NutritionalComponentStructure.getNutrititonalComponentStructure();
        String s;
        try {
            Double energy = this.getTotalNutritionalValues().get(ncs.getNutCompByName("ENERC"));
            energy = energy / 4.1868;
            s = dateString + " : " + String.format("%.2f", energy) + "kcal";
        } catch (NullPointerException ex) {
            s = dateString + " : 0 kcal";
        }
        return s;
    }

    /**
     * Information string to be displayed in ui of some sort.
     *
     * @return String containing information of this meal
     */
    public String info() {
        StringJoiner sj = new StringJoiner("\n");
        this.foodItems.forEach((k, v) -> {
            String pair = k.getName().split(",")[0] + " määrä: " + String.format("%.2f", v) + "g";
            sj.add(pair);
        });
        return sj.toString();
    }

}
