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
 * This class provides efficient access to FoodItem dataStructure from Database.
 * Based on Singleton pattern
 *
 * @author vili
 *
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
        this.idList = new ArrayList<>();
        this.nameList = new ArrayList<>();
        this.mapByName = new HashMap<>();
        this.mapById = new HashMap<>();
        this.itemList = fiList;
        fiList.stream().forEach(fi -> {
            this.mapByName.put(fi.getName(), fi);
            this.mapById.put(fi.getId(), fi);
            this.nameList.add(fi.getName());
            this.idList.add(fi.getId());

        });
    }

    /**
     *
     * @param filename filename to be used in DAO interface if this class has
     * not been initialised
     * @return Instance of this structure
     */
    public static FoodItemStructure getFoodItemStructure(String filename) {
        if (singleton == null) {
            singleton = new FoodItemStructure(filename);
        }
        return singleton;
    }

    public static void reset() {
        singleton = null;
    }

    /**
     *
     * @param id Id of the food requested
     * @return FoodItem that matches id, if not found null.
     */
    public FoodItem getFoodItemById(int id) {
        return this.mapById.get(id);
    }

    /**
     *
     * @param name Name of the food requested
     * @return FoodItem that matches name, if not found null.
     */
    public FoodItem getFoodItemByName(String name) {
        return this.mapByName.get(name);
    }

    public ArrayList<String> getNameList() {
        Collections.sort(this.nameList, String.CASE_INSENSITIVE_ORDER);
        return this.nameList;
    }

    /**
     * Filter list of names
     *
     * @param filter String that is user to filter namelist
     * @return List of filtered strings
     */
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
