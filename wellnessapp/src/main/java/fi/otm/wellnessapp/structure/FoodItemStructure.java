/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import fi.otm.wellnessapp.dao.FoodItemDaoSqlite3;
import fi.otm.wellnessapp.structure.FoodItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 *
 * @author vili
 */
public class FoodItemStructure {

    private HashMap<String, FoodItem> mapByName;
    private HashMap<Integer, FoodItem> mapById;
    private ArrayList<Integer> idList;
    private ArrayList<String> nameList;
    private ArrayList<FoodItem> itemList;
    private static FoodItemStructure singleton = null;

    private FoodItemStructure(String filename) {
        FoodItemDaoSqlite3 fiDao = new FoodItemDaoSqlite3(filename);
        ArrayList<FoodItem> fiList = fiDao.getAll();
        //System.out.println(fiList.get(1).getName());
        this.idList = new ArrayList<>();
        this.nameList = new ArrayList<>();
        this.mapByName = new HashMap<>();
        this.mapById = new HashMap<>();
        this.itemList = fiList;
        fiList.stream().forEach(fi -> {
            //System.out.println("DEBUG " + fi.getId());
            this.mapByName.put(fi.getName(), fi);
            this.mapById.put(fi.getId(), fi);
            this.nameList.add(fi.getName());
            this.idList.add(fi.getId());
            
        });
    }

    public static FoodItemStructure getFoodItemStructure(String filename) {
        if (singleton == null) {
            singleton = new FoodItemStructure(filename);
        }
        return singleton;
    }
    
    public static void reset() {
        singleton = null;
    }

    public FoodItem getFoodItemById(int id) {
        return this.mapById.get(id);
    }

    public FoodItem getFoodItemByName(String name) {
        return this.mapByName.get(name);
    }

    public ArrayList<String> getNameList() {
        Collections.sort(this.nameList, String.CASE_INSENSITIVE_ORDER);
        return this.nameList;
    }

    public ArrayList<String> filteredNameList(String filter) {
        ArrayList<String> fNameList = new ArrayList<>();
        this.getNameList().stream().filter(name -> name.toLowerCase().contains(filter.toLowerCase()))
                .forEach(name -> fNameList.add(name));
        return fNameList;
    }
    
    public ArrayList<FoodItem> getFiList() {
        return this.itemList;
    }

}
