/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class NutritionalComponentTest {
    NutritionalComponent nc;
    
    public NutritionalComponentTest() {
    }
    
    @Before
    public void setUp() {
        nc = new NutritionalComponent("Test", "Unit", "Class", "Classp");
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class NutritionalComponent.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        NutritionalComponent instance = nc;
        String result = instance.getName();
        assertEquals("Test", result);
    }



    /**
     * Test of getUnit method, of class NutritionalComponent.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        NutritionalComponent instance = nc;
        String expResult = "Unit";
        String result = instance.getUnit();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCmpClass method, of class NutritionalComponent.
     */
    @Test
    public void testGetCmpClass() {
        System.out.println("getCmpClass");
        NutritionalComponent instance = nc;
        String expResult = "Class";
        String result = instance.getCmpClass();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCmpClassp method, of class NutritionalComponent.
     */
    @Test
    public void testGetCmpClassp() {
        System.out.println("getCmpClassp");
        NutritionalComponent instance = nc;
        String expResult = "Classp";
        String result = instance.getCmpClassp();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class NutritionalComponent.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        NutritionalComponent instance = nc;
        int expResult = ((String)"Test").hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class NutritionalComponent.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = nc;
        NutritionalComponent instance = nc;
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        o = new NutritionalComponent("TEST2","Test","TEst","test");
        result = instance.equals(o);
        assertEquals(false, result);
    }
    
}
