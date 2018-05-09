/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import fi.otm.wellnessapp.dao.MealDao;
import fi.otm.wellnessapp.dao.MealDaoSqlite3;
import fi.otm.wellnessapp.dao.Sqlite3ConnectionManager;
import fi.otm.wellnessapp.dao.UserDao;
import fi.otm.wellnessapp.dao.UserDaoSqlite3;
import java.io.File;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class WellnessServiceTest {

    private final static String DB = "db/testDb.sqlite3";
    private final static String DBFOLDER = "db/";

    private WellnessService instance = null;

    private static User erkki = null;

    private static Meal erkkisOnlyMeal = null;

    public WellnessServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        File f = new File(DBFOLDER);
        f.mkdirs();
        Sqlite3ConnectionManager.reset();
        FoodItemStructure.reset();
        NutritionalComponentStructure.reset();
        Sqlite3ConnectionManager.reset();
        WellnessService.forceOtherFolder(DBFOLDER, DB);
        WellnessService.getInstance();
        User u = new User("Erkki Esimerkki", User.md5Hash("aasinpaa123"));
        erkki = u;
        UserDao ud = new UserDaoSqlite3(DB);
        ud.addUser(u);
        Meal m = new Meal(new Date(), erkki.getUserId());
        erkkisOnlyMeal = m;
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(DB);
        m.addFoodItem(fis.getFoodItemById(5), 200);
        MealDao md = new MealDaoSqlite3(DB);
        md.addOne(m);
    }

    @AfterClass
    public static void tearDownClass() {
        
        File f = new File(DB);
        f.delete();
        Sqlite3ConnectionManager.reset();
        FoodItemStructure.reset();
        NutritionalComponentStructure.reset();
        Sqlite3ConnectionManager.reset();
    }

    @Before
    public void setUp() {
        instance = WellnessService.getInstance();
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of createNewUser method, of class WellnessService.
     */
    @Test
    public void testCreateNewUser() {
        String username = "Minna Manninen";
        String password = "VaikeaSalaSana";
        instance.createNewUser(username, password);
        UserDao ud = new UserDaoSqlite3(DB);
        User u = ud.getUser(username, User.md5Hash(password));
        assertEquals(u.getUserName(), username);
        ud.removeUser(u);
    }

    /**
     * Test of login method, of class WellnessService.
     */
    @Test
    public void testLogin() {
        String username = "Erkki Esimerkki";
        String password = "aasinpaa123";
        boolean expResult = false;
        boolean result = instance.login("I do not exist", "Forgotten");
        assertEquals(expResult, result);
        result = instance.login(username, password);
        assertTrue(result);
        assertEquals(username, instance.getUser().getUserName());
    }

    /**
     * Test of getDataBaseName method, of class WellnessService.
     */
    @Test
    public void testGetDataBaseName() {
        String expResult = DB;
        String result = instance.getDataBaseName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNewMeal method, of class WellnessService And getNewMeal.
     */
    @Test
    public void testSetNewMeal() {
        Meal meal = new Meal(new Date(), 1);
        instance.setNewMeal(meal);
        assertEquals(meal, instance.getNewMeal());
    }

    /**
     * Test of addFoodToNewMeal method, of class WellnessService. And
     * removeFoodItemFromNewMeal
     */
    @Test
    public void testAddFoodToNewMeal() {
        Meal meal = new Meal(new Date(), 1);
        instance.setNewMeal(meal);
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(DB);
        FoodItem fi = fis.getFoodItemById(8);
        Double amount = 60.d;
        instance.addFoodToNewMeal(fi, amount);
        assertEquals(amount, instance.getNewMeal().getFoodItems().get(fi), 0.0000000000001);
        instance.removeFoodItemFromNewMeal(fi.getName());
        assertEquals(null, instance.getNewMeal().getFoodItems().get(fi));
    }

    /**
     * Test of addNewMealToUser method, of class WellnessService.
     */
    @Test
    public void testAddNewMealToUser() {
        instance.setUser(erkki);
        instance.addNewMealToUser();
        instance.setNewMeal(new Meal(new Date(), erkki.getUserId()));
        instance.addNewMealToUser();
        assertEquals(0, erkki.getMealList().size());
        Meal m = new Meal(new Date(), erkki.getUserId());
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(DB);
        m.addFoodItem(fis.getFoodItemById(8), 80);
        instance.setNewMeal(m);
        instance.addNewMealToUser();
        assertEquals(1, erkki.getMealList().size());
        erkki.getMealList().clear();
    }

    /**
     * Test of getFis method, of class WellnessService.
     */
    @Test
    public void testGetFis() {
        FoodItemStructure result = instance.getFis();
        assertTrue(2000 < result.getNameList().size());
    }

    /**
     * Test of updateUserCalorieGoal method, of class WellnessService.
     */
    @Test
    public void testUpdateUserCalorieGoal() {
        instance.setUser(erkki);
        int goal = 3000;
        instance.updateUserCalorieGoal(goal);
        UserDao ud = new UserDaoSqlite3(DB);
        User u = ud.getUser(erkki.getUserName(), erkki.getPasswordHash());
        assertEquals(goal, u.getDailyCalorieGoal());
        instance.updateUserCalorieGoal(2000);
    }

    /**
     * Test of updateUserPassword method, of class WellnessService.
     */
    @Test
    public void testUpdateUserPassword() {
        String password = "asdfjlk";
        String confirmation = "132456253";
        instance.setUser(erkki);
        boolean expResult = false;
        boolean result = instance.updateUserPassword(password, confirmation);
        assertEquals(expResult, result);
        password = "aasdf";
        assertTrue(instance.updateUserPassword(password, password));
    }

    /**
     * Test of removeMeal method, of class WellnessService.
     */
    @Test
    public void testRemoveMeal() {
        instance.setUser(erkki);
        Meal meal = erkkisOnlyMeal;
        instance.removeMeal(meal);
        assertEquals(0, erkki.getMealList().size());
    }

}
