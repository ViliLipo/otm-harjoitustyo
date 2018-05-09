/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.NutritionalComponent;
import java.util.List;

/**
 *
 * @author vili
 */
public interface NutritionalComponentDao {

    /**
     *
     * @return List of all NutritionalComponents in the database. On fail it
     * will be a empty list.
     */
    public List<NutritionalComponent> getAll();

    /**
     * Gets one NutritionalComponent based on its name.
     *
     * @param eufdName Name to be matched.
     * @return NutritionalComponent if match was found, else null.
     */
    public NutritionalComponent getOne(String eufdName);

    /**
     * Writes one NutritionalComponent to database. Fails silently.
     *
     * @param nc NutritionalComponent to be written.
     */
    public void add(NutritionalComponent nc);

    /**
     * Removes one nutritionalComponent from database.
     *
     * @param eufdName name for matching
     */
    public void remove(String eufdName);
}
