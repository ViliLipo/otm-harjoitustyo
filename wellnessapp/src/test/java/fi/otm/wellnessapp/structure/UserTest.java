/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import fi.otm.wellnessapp.dao.Sqlite3ConnectionManager;
import fi.otm.wellnessapp.tools.Sqlite3Utils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
public class UserTest {

    private User user;
    private FoodItemStructure fis;
    private final String dataBaseName = "db/testDb.sqlite3";
    private final String dbPath = "db/";
    private NutritionalComponentStructure ncs;

    public UserTest() {
    }

    @BeforeClass
    public static void oneTimeSetup() {
        Sqlite3Utils s3u = new Sqlite3Utils();
        s3u.initDb("sqlite/dataBaseSchema.sqlite3",
                "db/testDb.sqlite3", "db/", "csv/component.csv",
                "csv/foodname_FI.csv", "csv/component_value.csv");

    }

    @Before
    public void setUp() {
        Sqlite3ConnectionManager.reset();
        user = new User("Matti", User.md5Hash("crypted"));
        fis = FoodItemStructure.getFoodItemStructure(dataBaseName);
        ncs = NutritionalComponentStructure.getNutrititonalComponentStructure(dataBaseName);
    }

    @After
    public void tearDown() {
        Sqlite3ConnectionManager.reset();
        fis.reset();
        ncs.reset();
    }
    
    @AfterClass
    public static void oneTimeTearDown() {
        File file = new File("db/testDb.sqlite3");
        file.delete();
    }

    /**
     * Test of setUserID method, of class User. an getUserId of class user.
     */
    @Test
    public void testSetUserID() {
        //System.out.println("setUserID");
        int id = 65;
        User instance = user;
        instance.setUserID(id);
        assertEquals(id, user.getUserId());
    }

    /**
     * Test of getUserName method, of class User.
     */
    @Test
    public void testGetUserName() {
        //System.out.println("getUserName");
        User instance = user;
        String expResult = "Matti";
        String result = instance.getUserName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassWord method, of class User.
     */
    @Test
    public void testSetPassword() {
        //System.out.println("setPassword");
        String expResult = User.md5Hash("password");
        user.setPassword("password");
        assertTrue(expResult.contentEquals(user.getPasswordHash()));
    }

    /**
     * Test of getPasswordHash method, of class User.
     */
    @Test
    public void testGetPasswordHash() {
        //System.out.println("getPasswordHash");
        User instance = user;
        String password = "crypted";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                md.update(password.getBytes("UTF-8"));
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                String expResult = bigInt.toString(16);
                String result = instance.getPasswordHash();
                //System.out.println(result);
                //System.out.println(expResult);
                assertTrue(expResult.contentEquals(result));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of setCalorieGoal method, of class User. And test of getCalorieGoal
     */
    @Test
    public void testSetCalorieGoal() {
        //System.out.println("setCalorieGoal");
        int goal = 3500;
        User instance = user;
        instance.setCalorieGoal(goal);
        assertEquals(3500, instance.getDailyCalorieGoal(), 0.001);
    }

    /**
     * Test of setDailyKiloJouleGoal method, of class User. And Test of
     * getDailyKiloJouleGoal
     */
    @Test
    public void testSetDailyKiloJouleGoal() {
        //System.out.println("setDailyKiloJouleGoal");
        double value = 3500.0;
        User instance = user;
        instance.setDailyKiloJouleGoal(value);
        int expResult = 3500;
        assertEquals(expResult, user.getDailyKiloJouleGoal());
    }

    /**
     * Test of getDailyProteinGoal method, of class User. And test of
     * setDailyProteinGoal
     */
    @Test
    public void testGetDailyProteinGoal() {
        //System.out.println("getDailyProteinGoal");
        User instance = user;
        user.setDailyProteinGoal(20.00);
        double expResult = 20.00;
        double result = instance.getDailyProteinGoal();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setMealList method, of class User. And get mealList
     */
    @Test
    public void testSetMealList() {
        //System.out.println("setMealList");
        ArrayList<Meal> list = new ArrayList<>();
        list.add(new Meal(new Date(), 1));
        User instance = user;
        instance.setMealList(list);
        ArrayList<Meal> result = instance.getMealList();
        assertEquals(list.get(0), result.get(0));
    }

    /**
     * Test of addMeal method, of class User.
     */
    @Test
    public void testAddMeal() {
        //System.out.println("addMeal");
        Meal meal = new Meal(new Date(), 0);
        User instance = user;
        instance.addMeal(meal);
        assertEquals(meal, user.getMealList().get(0));
    }

    /**
     * Test of weekBeforeDate method, of class User.
     */
    @Test
    public void testWeekBeforeDate() {
        //System.out.println("weekBeforeDate");
        Date time = new Date();
        Date time2 = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(time2);
        cl.add(Calendar.DATE, -8);
        time2 = cl.getTime();
        User instance = user;
        Meal meal1 = new Meal(time, user.getUserId());
        meal1.addFoodItem(fis.getFoodItemById(5), 100);
        meal1.addFoodItem(fis.getFoodItemById(8), 50);
        Meal meal2 = new Meal(time, user.getUserId());
        meal2.addFoodItem(fis.getFoodItemById(6), 100);
        Meal meal3 = new Meal(time2, user.getUserId());
        meal3.addFoodItem(fis.getFoodItemById(10), 50);
        user.addMeal(meal1);
        user.addMeal(meal2);
        user.addMeal(meal3);
        ArrayList<HashMap<NutritionalComponent, Double>> list = user.weekBeforeDate(new Date());
        assertEquals(7, list.size());
        HashMap<NutritionalComponent, Double> first = user.dailyScore(new Date());
        assertEquals(first, list.get(0));
        HashMap<NutritionalComponent, Double> last = new HashMap<>();
        assertEquals(last, list.get(6));

    }

    /**
     * Test of getWeeksCalories method, of class User.
     */
    @Ignore("WIP")
    @Test
    public void testGetWeeksCalories() {
        //System.out.println("getWeeksCalories");
        Date time = null;
        User instance = null;
        ArrayList<Double> expResult = null;
        ArrayList<Double> result = instance.getWeeksCalories(time);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dailyScore method, of class User.
     */
    @Test
    public void testDailyScore() {
        //System.out.println("dailyScore");
        Date time = new Date();
        Date time2 = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(time2);
        cl.add(Calendar.DATE, -1);
        time2 = cl.getTime();
        User instance = user;
        Meal meal1 = new Meal(time, user.getUserId());
        meal1.addFoodItem(fis.getFoodItemById(5), 100);
        meal1.addFoodItem(fis.getFoodItemById(8), 50);
        Meal meal2 = new Meal(time, user.getUserId());
        meal2.addFoodItem(fis.getFoodItemById(6), 100);
        Meal meal3 = new Meal(time2, user.getUserId());
        meal3.addFoodItem(fis.getFoodItemById(10), 50);
        user.addMeal(meal1);
        user.addMeal(meal2);
        user.addMeal(meal3);
        HashMap<NutritionalComponent, Double> result = instance.dailyScore(time);
        Double expectedCalories = fis.getFoodItemById(5).getContents().get(ncs.getNutCompByName("ENERC"))
                + (fis.getFoodItemById(8).getContents().get(ncs.getNutCompByName("ENERC")) / 2)
                + fis.getFoodItemById(6).getContents().get(ncs.getNutCompByName("ENERC"));
        assertEquals(expectedCalories, result.get(ncs.getNutCompByName("ENERC")), 0.001);
    }

}
