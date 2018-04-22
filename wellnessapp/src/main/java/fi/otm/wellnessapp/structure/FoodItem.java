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
public class FoodItem implements Comparable {

    private final int id;
    private final String name;
    private final String language;
    private HashMap<NutritionalComponent, Double> contents;
    private HashMap<String, NutritionalComponent> contentName;

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

    public void addContents(double amount, NutritionalComponent nc) {
        Double a = amount;
        this.contents.put(nc, a);
        this.contentName.put(nc.getName(), nc);
    }

    public HashMap<NutritionalComponent, Double> getContents() {
        return this.contents;
    }

    public String info() {
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
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            FoodItem fo = (FoodItem) o;
            return this.id == fo.getId();
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(this.getClass())) {
            FoodItem fo = (FoodItem) o;
            if (this.id > fo.getId()) {
                return 1;
            } else if (this.id < fo.getId()) {
                return -1;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
