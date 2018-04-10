/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.NutritionalComponent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vili
 * This is a singleton class for globally accessing 
 * data structure for resolving NutritionalComponents
 * based on their name.
 * It saves space.
 */
public class NutritionalComponentStructure {
    
    private final HashMap<String, NutritionalComponent> structure;
    private final ArrayList<String> nameList;
    private final String filename;
    private static NutritionalComponentStructure SINGLETON = null;
    
    private NutritionalComponentStructure (String filename) {
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
        if(SINGLETON == null) {
            SINGLETON = new NutritionalComponentStructure(filename);
        }
        return SINGLETON;
    }
    
    public NutritionalComponent getNutCompByName(String name) {
        return this.structure.get(name);
    }
    
    
}