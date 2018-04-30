/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.util.HashMap;

/**
 * Class that handles information of foods
 * @author vili
 */
public class FoodItem implements Comparable<FoodItem> {

    private final int id;
    private final String name;
    private final String language;
    private HashMap<NutritionalComponent, Double> contents;
    private HashMap<String, NutritionalComponent> contentName;
    
    /**
     * 
     * @param id    Database id of this FoodItem
     * @param name  Name of this FoodItem
     * @param language  Language of this FoodItem
     */
    public FoodItem(int id, String name, String language) {
        this.id = id;
        this.name = name;
        contents = new HashMap<>();
        contentName = new HashMap<>();
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
    /**
     * Adds nutritional component to this food.
     * @param amount Double in unit specified in NutritionalComponent
     * @param nc NutritionalComponent to be added
     */
    public void addContents(double amount, NutritionalComponent nc) {
        Double a = amount;
        this.contents.put(nc, a);
        this.contentName.put(nc.getName(), nc);
    }

    public HashMap<NutritionalComponent, Double> getContents() {
        return this.contents;
    }
    
    /**
     * Build String containing relevant information
     * @return String containing relevant information of this FoodItem 
     */
    public String info() {
        try {
            String info = this.name + "\n";
            Double enerVal = this.getContents().get(this.contentName.get("ENERC")) / 4.1868;
            String energy = "Energiaa : " + String.format("%.2f", enerVal) + "kcal/100g\n";
            Double fatVal = this.getContents().get(this.contentName.get("FAT"));
            String fat = "Rasvaa : " + String.format("%.2f", fatVal) + "g/100g\n";
            Double protVal = this.getContents().get(this.contentName.get("PROT"));
            String prot = "Proteiinia : " + String.format("%.2f", protVal) + "g/100g\n";
            Double sugarVal = this.getContents().get(this.contentName.get("SUGAR"));
            String sugar = "Sokeria : " + String.format("%.2f", sugarVal) + "g/100g\n";
            info = info + energy + fat + prot + sugar;
            return info;
        } catch (NullPointerException ex) {
            return "Some nutritional info cannot be found\n"
                    + "Dataset might be corrupted";
        }
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        try {
            if (o.getClass().equals(this.getClass())) {
                FoodItem fo = (FoodItem) o;
                return this.id == fo.getId();
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        }

    }

    @Override
    public int compareTo(FoodItem fo) {
        if (this.id > fo.getId()) {
            return 1;
        } else if (this.id < fo.getId()) {
            return -1;
        } else {
            return 0;
        }
    }
    @Override
    public String toString() {
        return this.name;
    }
}
