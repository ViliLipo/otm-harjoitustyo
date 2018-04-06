/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.Meal;
import java.util.List;

/**
 *
 * @author vili
 */
public interface MealDao {
    
    public List<Meal> getAll();
    public List<Meal> getByUserId(int userid);
    public Meal getOne(int mealId);
    public void addOne(Meal meal);
    public void addAll(List<Meal> mealList);
    public void remove(Meal meal);
    
    
    
}
