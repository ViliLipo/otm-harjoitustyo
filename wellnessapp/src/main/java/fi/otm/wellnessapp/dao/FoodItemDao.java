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
    public List<FoodItem> getAll();
    public FoodItem getOne(int foodItemID);
    public void addOne(FoodItem fi);
    public void addAll(List<FoodItem> fiList);
    public void remove(FoodItem fi);
    
}
