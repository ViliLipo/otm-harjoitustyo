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

    /**
     *
     * @return All meals in the database.
     */
    public List<Meal> getAll();

    /**
     * List of all meals associated with certain user.
     *
     * @param userid id number of associated user.
     * @return list of users meals. If there is none empty list will be
     * returned.
     */
    public List<Meal> getByUserId(int userid);

    /**
     * Get one meal based on meals, id.
     *
     * @param mealId id number to match
     * @return meal that matches, if none then null.
     */
    public Meal getOne(int mealId);

    /**
     * Write one meal to database. Fails silently Also sets Meals, mealId field
     * to match value in database.
     *
     * @param meal Meal to write
     */
    public void addOne(Meal meal);

    /**
     * Write all meals to database in one transaction. Preferable to looping add one
     * since transactions are extremely time consuming. Sets value for mealId to all
     * meals to match database.
     *
     * @param mealList list of meals to write.
     */
    public void addAll(List<Meal> mealList);

    /**
     * Removes given meal from database.
     *
     * @param meal Meal to be removed.
     */
    public void remove(Meal meal);

}
