/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItemStructure;
import fi.otm.wellnessapp.structure.Meal;
import fi.otm.wellnessapp.structure.NutritionalComponentStructure;
import fi.otm.wellnessapp.tools.Sqlite3Utils;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author vili
 */
public class MealDaoSqlite3Test {

    private static String dbName = "db/mealtestDb.sqlite3";
    private static String dbPath = "db/";
    private static Date refer = new Date();
    private FoodItemStructure fis;

    public MealDaoSqlite3Test() {
        NutritionalComponentStructure.getNutrititonalComponentStructure(dbName);
        fis = FoodItemStructure.getFoodItemStructure(dbName);
        String sqlInsert = "INSERT OR IGNORE INTO Meal VALUES(?,?,?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            PreparedStatement prep = conn.prepareStatement(sqlInsert);
            prep.setInt(1, 1);
            prep.setInt(2, 1);
            prep.setTimestamp(3, new java.sql.Timestamp(refer.getTime()));
            prep.executeUpdate();
            prep.setInt(1, 2);
            prep.setInt(2, 1);
            prep.setTimestamp(3, new java.sql.Timestamp(refer.getTime() + 100));
            prep.executeUpdate();
            prep.setInt(1, 3);
            prep.setInt(2, 2);
            prep.setTimestamp(3, new java.sql.Timestamp(refer.getTime()));
            prep.executeUpdate();
            sqlInsert = "INSERT OR IGNORE INTO InAMeal VALUES(?,?,?)";
            prep = conn.prepareStatement(sqlInsert);
            prep.setInt(1, 1);
            prep.setInt(2, 6);
            prep.setString(3, "60.00");
            prep.executeUpdate();
            prep.setInt(1, 2);
            prep.setInt(2, 8);
            prep.setString(3, "80.00");
            prep.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @BeforeClass
    public static void oneTimeSetup() {
        Sqlite3ConnectionManager.reset();
        FoodItemStructure.reset();
        NutritionalComponentStructure.reset();
        Sqlite3Utils s3u = new Sqlite3Utils();
        s3u.initDb("sqlite/dataBaseSchema.sqlite3",
                dbName, dbPath, "csv/component.csv",
                "csv/foodname_FI.csv", "csv/component_value.csv");
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @AfterClass
    public static void rmDb() {
        File file = new File("db/mealtestDb.sqlite3");
        file.delete();
        Sqlite3ConnectionManager.reset();
        FoodItemStructure.reset();
        NutritionalComponentStructure.reset();
    }

    /**
     * Test of getAll method, of class MealDaoSqlite3.
     */
    @Test
    public void testGetAll() {
        MealDaoSqlite3 instance = new MealDaoSqlite3(dbName);
        ArrayList<Meal> result = instance.getAll();
        assertEquals(1, result.get(0).getMealId());
        assertEquals(3, result.get(2).getMealId());
    }

    /**
     * Test of getByUserId method, of class MealDaoSqlite3.
     */
    @Test
    public void testGetByUserId() {
        int userid = 1;
        MealDaoSqlite3 instance = new MealDaoSqlite3(dbName);
        ArrayList<Meal> result = instance.getByUserId(userid);
        assertEquals(2, result.size());
    }

    /**
     * Test of getOne method, of class MealDaoSqlite3.
     */
    @Test
    public void testGetOne() {
        int mealId = 1;
        MealDaoSqlite3 instance = new MealDaoSqlite3(dbName);
        Meal expResult = new Meal(refer, 1);
        expResult.setMealId(1);
        Meal result = instance.getOne(mealId);
        assertEquals(expResult.getMealId(), result.getMealId());
    }

    /**
     * Test of addOne method, of class MealDaoSqlite3.
     */
    @Test
    public void testAddOne() {
        Meal meal = new Meal(new Date(refer.getTime() + 6000), 1);
        meal.addFoodItem(fis.getFoodItemById(10), 100);
        meal.addFoodItem(fis.getFoodItemById(11), 50);
        MealDaoSqlite3 instance = new MealDaoSqlite3(dbName);
        instance.addOne(meal);
        ArrayList<Meal> list = instance.getAll();
        assertTrue(list.contains(meal));
        instance.remove(meal);
    }

    /**
     * Test of remove method, of class MealDaoSqlite3.
     */
    @Test
    public void testRemove() {
        Meal meal = new Meal(new Date(refer.getTime() + 6000), 1);
        meal.setMealId(4);
        MealDaoSqlite3 instance = new MealDaoSqlite3(dbName);
        instance.remove(meal);
        ArrayList<Meal> list = instance.getAll();
        assertFalse(list.contains(meal));
    }

}
