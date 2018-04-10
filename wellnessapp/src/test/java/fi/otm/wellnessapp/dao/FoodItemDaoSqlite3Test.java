/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItem;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class FoodItemDaoSqlite3Test {
    
    public FoodItemDaoSqlite3Test() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        FoodItemDaoSqlite3 instance = null;
        ArrayList<FoodItem> expResult = null;
        ArrayList<FoodItem> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOne method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testGetOne() {
        System.out.println("getOne");
        int foodItemID = 0;
        FoodItemDaoSqlite3 instance = null;
        FoodItem expResult = null;
        FoodItem result = instance.getOne(foodItemID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addOne method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testAddOne() {
        System.out.println("addOne");
        FoodItem fi = null;
        FoodItemDaoSqlite3 instance = null;
        instance.addOne(fi);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAll method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testAddAll() {
        System.out.println("addAll");
        List<FoodItem> fiList = null;
        FoodItemDaoSqlite3 instance = null;
        instance.addAll(fiList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        FoodItem fi = null;
        FoodItemDaoSqlite3 instance = null;
        instance.remove(fi);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
