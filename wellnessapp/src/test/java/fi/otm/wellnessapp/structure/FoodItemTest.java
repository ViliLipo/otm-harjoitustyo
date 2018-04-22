/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class FoodItemTest {
    private FoodItem fi;
    
    public FoodItemTest() {
    }
    
    @Before
    public void setUp() {
        fi = new FoodItem(1, "lihapulla", "fi");
        fi.addContents(100, new NutritionalComponent("ENERC", "kj", "ENER", "ENER"));
        fi.addContents(20, new NutritionalComponent("FAT", "g", "FAT", "FAT"));
        fi.addContents(10, new NutritionalComponent("PROT", "g", "PROT", "PROT"));
        fi.addContents(5, new NutritionalComponent("SUGAR", "g", "SUG", "SUG"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class FoodItem.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        FoodItem instance = fi;
        String expResult = "lihapulla";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class FoodItem.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        FoodItem instance = fi;
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLanguage method, of class FoodItem.
     */
    @Test
    public void testGetLanguage() {
        System.out.println("getLanguage");
        FoodItem instance = fi;
        String expResult = "fi";
        String result = instance.getLanguage();
        assertEquals(expResult, result);
    }

    /**
     * Test of addContents method, of class FoodItem.
     */
    @Test
    public void testAddContents() {
        System.out.println("addContents");
        double amount = 75.0;
        NutritionalComponent nc = new NutritionalComponent("Test","Test","Test","Test");
        FoodItem instance = fi;
        instance.addContents(amount, nc);
        assertTrue(fi.getContents().containsKey(nc));
    }


    /**
     * Test of hashCode method, of class FoodItem.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        FoodItem instance = fi;
        int expResult = 1;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class FoodItem.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = fi;
        FoodItem instance = fi;
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        o = new FoodItem(2, "Makarooni", "fi");
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
        result = instance.equals(new Object());
        assertFalse(result);
    }

    /**
     * Test of compareTo method, of class FoodItem.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Object o = new FoodItem(2, "Makarooni", "fi");
        FoodItem instance = fi;
        int expResult = -1;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        assertEquals(0, fi.compareTo(fi));
        assertEquals(1, ((FoodItem)o).compareTo(fi));
        assertEquals(0, fi.compareTo(new Object()));
    }
    @Test
    public void testInfo() {
        String expResult = "lihapulla\n"
                + "Energiaa : 23.88kcal/100g\n"
                + "Rasvaa : 20.00g/100g\n"
                + "Proteiinia : 10.00g/100g\n"
                + "Sokeria : 5.00g/100g\n";
        System.out.println(expResult);
        System.out.println(fi.info());
        assertEquals(expResult, fi.info());
    }
}
