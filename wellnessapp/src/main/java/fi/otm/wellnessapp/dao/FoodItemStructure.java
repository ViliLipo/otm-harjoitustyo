/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItem;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vili
 */
public class FoodItemStructure {
    
    private HashMap<String, FoodItem> mapByName;
    private HashMap<Integer, FoodItem> mapById;
    private ArrayList<Integer> idList;
    private ArrayList<String> nameList;
    private static FoodItemStructure SINGLETON = null;
    
    private FoodItemStructure(String filename) {
        FoodItemDaoSqlite3 fiDao = new FoodItemDaoSqlite3(filename);
        ArrayList<FoodItem> fiList = fiDao.getAll();
        //System.out.println(fiList.get(1).getName());
        this.idList = new ArrayList<>();
        this.nameList = new ArrayList<>();
        this.mapByName = new HashMap<>();
        this.mapById = new HashMap<>();
        fiList.stream().forEach(fi -> {
            //System.out.println("DEBUG " + fi.getId());
            this.mapByName.put(fi.getName(), fi);
            this.mapById.put(fi.getId(), fi);
            this.nameList.add(fi.getName());
            this.idList.add(fi.getId());
        });
    }
    
    public static FoodItemStructure getFoodItemStructure(String filename) {
        if(SINGLETON == null) {
            SINGLETON = new FoodItemStructure(filename);
        }
        return SINGLETON;
    }
    
    public FoodItem getFoodItemById(int id) {
        return this.mapById.get(id);
    }
    
    public FoodItem getFoodItemByName(String name) {
        return this.mapByName.get(name);
    }
    
    public ArrayList<String> getNameList() {
        return this.nameList;
    }
    
    
    
}