/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import fi.otm.wellnessapp.dao.FoodItemDao;
import fi.otm.wellnessapp.dao.FoodItemDaoSqlite3;
import fi.otm.wellnessapp.dao.Sqlite3ConnectionManager;
import fi.otm.wellnessapp.tools.Sqlite3Utils;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class MealTest {

    private Meal meal;
    private String dbName = "db/fullTestDb.sqlite3";
    private String dbPath = "db/";
    private Date refer;

    public MealTest() {
        Sqlite3Utils s3u = new Sqlite3Utils();
        s3u.initDb("sqlite/dataBaseSchema.sqlite3",
                dbName, dbPath, "csv/component.csv",
                "csv/foodname_FI.csv", "csv/component_value.csv");
        NutritionalComponentStructure.getNutrititonalComponentStructure(dbName);
        FoodItemStructure.getFoodItemStructure(dbName);
    }

    @Before
    public void setUp() {
        refer = new Date();
        meal = new Meal(refer, 5);
        NutritionalComponentStructure.reset();
        FoodItemStructure.reset();
        Sqlite3ConnectionManager.reset();
    }

    @After
    public void tearDown() {
    }
    @AfterClass
    public static void resetDb() {
        File file  = new File("db/fullTestDb.sqlite3");
        file.delete();
    }

    /**
     * Test of setMealId method, of class Meal.
     */
    @Test
    public void testSetGetMealId() {
        //System.out.println("setMealId");
        int id = 0;
        meal.setMealId(id);
        assertEquals(id, meal.getMealId());
    }

    /**
     * Test of getUserId method, of class Meal.
     */
    @Test
    public void testGetUserId() {
        //System.out.println("getUserId");
        Meal instance = meal;
        int expResult = 5;
        int result = instance.getUserId();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFoodItem method, of class Meal.
     */
    @Test
    public void testAddFoodItem() {
        //System.out.println("addFoodItem");
        FoodItem fi = new FoodItem(1, "lihapulla", "fi");
        double amount = 100.0;
        Meal instance = meal;
        instance.addFoodItem(fi, amount);
        assertTrue(meal.getFoodItems().containsKey(fi));
    }

    /**
     * Test of removeFoodItem method, of class Meal.
     */
    @Test
    public void testRemoveFoodItem() {
        //System.out.println("removeFoodItem");
        FoodItem fi = new FoodItem(1, "lihapulla", "fi");
        double amount = 100.0;
        Meal instance = meal;
        instance.addFoodItem(fi, amount);
        instance.removeFoodItem(fi);
        assertFalse(meal.getFoodItems().containsKey(fi));
    }

    /**
     * Test of getTotalNutritionalValues method, of class Meal.
     */
    @Test
    public void testGetTotalNutritionalValues() {
        //System.out.println("getTotalNutritionalValues");
        FoodItemDao foodDao = new FoodItemDaoSqlite3(dbName);
        FoodItem f1 = foodDao.getOne(4);
        FoodItem f2 = foodDao.getOne(6);
        Meal instance = meal;
        instance.addFoodItem(f1, 200);
        instance.addFoodItem(f2, 100);
        NutritionalComponentStructure ncs = NutritionalComponentStructure.getNutrititonalComponentStructure(dbName);
        Double expResult = 2 * f1.getContents().get(ncs.getNutCompByName("ENERC"))
                + 1 * f2.getContents().get(ncs.getNutCompByName("ENERC"));
        HashMap<NutritionalComponent, Double> result = instance.getTotalNutritionalValues();
        assertEquals(expResult, result.get(ncs.getNutCompByName("ENERC")));
        result = instance.getTotalNutritionalValues();
        assertEquals(expResult, result.get(ncs.getNutCompByName("ENERC")));
    }

    /**
     * Test of getFoodItems method, of class Meal.
     */
    @Test
    public void testGetFoodItems() {
        //System.out.println("getFoodItems");
        Meal instance = meal;
        FoodItemDao foodDao = new FoodItemDaoSqlite3(dbName);
        FoodItem f1 = foodDao.getOne(4);
        FoodItem f2 = foodDao.getOne(6);
        instance.addFoodItem(f1, 200);
        instance.addFoodItem(f2, 100);
        HashMap<FoodItem, Double> expResult = new HashMap<>();
        expResult.put(f1, 200.d);
        expResult.put(f2, 100.d);
        HashMap<FoodItem, Double> result = instance.getFoodItems();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTime method, of class Meal.
     */
    @Test
    public void testGetTime() {
        //System.out.println("getTime");
        Meal instance = meal;
        Date expResult = refer;
        Date result = instance.getTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Meal.
     */
    @Test
    public void testCompareTo() {
        //System.out.println("compareTo");
        Meal o = meal;
        Meal instance = meal;
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Meal.
     */
    @Test
    public void testToString() {
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(dbName);
        meal.addFoodItem(fis.getFoodItemById(5), 100);
        DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String dateString = df.format(refer);
        NutritionalComponentStructure ncs = NutritionalComponentStructure.getNutrititonalComponentStructure();
        Double energy = meal.getTotalNutritionalValues().get(ncs.getNutCompByName("ENERC"));
        energy = energy / 4.1868;
        String s = dateString + " : " + String.format("%.2f", energy) + "kcal";
        assertEquals(s, meal.toString());
    }

}
