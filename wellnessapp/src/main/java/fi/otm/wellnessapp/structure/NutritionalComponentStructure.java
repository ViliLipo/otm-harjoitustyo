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
 * This is a singleton class for globally accessing data structure for resolving
 * NutritionalComponents based on their name. It saves space.
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
     * @param filename File name of application database
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
    /**
     * Get instance of this structure
     * @param filename Filename of the database for DAO
     * @return instance of this structure
     */
    public static NutritionalComponentStructure getNutrititonalComponentStructure(String filename) {
        if (singleton == null) {
            singleton = new NutritionalComponentStructure(filename);
        }
        return singleton;
    }
    
    /** If this singleton has been already initialised this can be used
     * to access it. Will return null, if it has not been initialised.
     * 
     * @return instance of this structure
     */
    public static NutritionalComponentStructure getNutrititonalComponentStructure() {
        return singleton;
    }
    
    /**
     * Forces reinitialisation next time this structure is accessed.
     */
    public static void reset() {
        singleton = null;
    }

    /**
     *
     * @param name Name of the Nutritionalcomponent requested
     * @return NutritionalComponent the component that the name matched.
     */
    public NutritionalComponent getNutCompByName(String name) {
        return this.structure.get(name);
    }

}
