/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import fi.otm.wellnessapp.dao.NutritionalComponentDaoSqlite3;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a singleton class for globally accessing data structure
 * for resolving NutritionalComponents based on their name. It saves space.
 * 
 * @author vili 
 */
public class NutritionalComponentStructure {

    private final HashMap<String, NutritionalComponent> structure;
    private final ArrayList<String> nameList;
    private final String filename;
    private static NutritionalComponentStructure singleton = null;
    
    /**
     * 
     * @param filename     File name of application database 
     */
    
    private NutritionalComponentStructure(String filename) {
        this.filename = filename;
        NutritionalComponentDaoSqlite3 ncDao = new NutritionalComponentDaoSqlite3(this.filename);
        ArrayList<NutritionalComponent> nclist = ncDao.getAll();
        this.structure = new HashMap<>();
        this.nameList = new ArrayList<>();
        for (NutritionalComponent nc : nclist) {
            this.structure.put(nc.getName(), nc);
            this.nameList.add(nc.getName());
        }
    }

    public static NutritionalComponentStructure getNutrititonalComponentStructure(String filename) {
        if (singleton == null) {
            singleton = new NutritionalComponentStructure(filename);
        }
        return singleton;
    }
    public static NutritionalComponentStructure getNutrititonalComponentStructure() {
        if (singleton == null) {
            return null;
        }
        return singleton;
    }
    public static void reset() {
        singleton = null;
    }
    /**
     * 
     * @param name Name of the Nutritionalcomponent requested
     * @return NutritionalComponent     the component that the name matched.
     */
    public NutritionalComponent getNutCompByName(String name) {
        return this.structure.get(name);
    }

}
