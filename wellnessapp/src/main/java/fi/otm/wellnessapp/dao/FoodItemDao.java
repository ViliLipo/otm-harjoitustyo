/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItem;
import java.util.List;

/**
 *
 * @author vili
 */
public interface FoodItemDao {

    /**
     *
     * @return List of all FoodItems in database. On fail it will be a empty
     * list.
     */
    public List<FoodItem> getAll();

    /**
     *
     * @param foodItemID id of the wanted FoodItem
     * @return Matching food item if found, else null
     */
    public FoodItem getOne(int foodItemID);

    /**
     * Writes one FoodItem to database, fails silently.
     *
     * @param fi FoodItem to be written.
     */
    public void addOne(FoodItem fi);

    /**
     * Adds all FoodItems to database in one commit. preferable to looping
     * addOne since commits are extremely time consuming.
     *
     * @param fiList FoodItems to add.
     */
    public void addAll(List<FoodItem> fiList);

    /**
     * removes foodItem from the database that matches given FoodItem
     *
     * @param fi FoodItem to be removed
     */
    public void remove(FoodItem fi);

}
