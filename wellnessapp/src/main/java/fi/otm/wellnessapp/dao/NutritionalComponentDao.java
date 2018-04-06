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
    public List<NutritionalComponent> getAll();
    public NutritionalComponent getOne(String eufdName);
    public void add(NutritionalComponent nc);
    public void remove(String eufdName);
}
