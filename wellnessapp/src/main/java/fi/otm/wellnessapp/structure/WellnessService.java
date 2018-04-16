/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import fi.otm.wellnessapp.dao.MealDao;
import fi.otm.wellnessapp.dao.MealDaoSqlite3;
import fi.otm.wellnessapp.dao.UserDao;
import fi.otm.wellnessapp.dao.UserDaoSqlite3;
import fi.otm.wellnessapp.tools.Sqlite3Utils;

/**
 *
 * @author vili
 */
public class WellnessService {

    private static WellnessService singleton;
    private User user;
    private String dataBaseName;
    private FoodItemStructure fis;
    private NutritionalComponentStructure ncs;
    private Meal newMeal;

    private WellnessService(String dataBaseName) {
        this.dataBaseName = dataBaseName;
        Sqlite3Utils.initDb("src/main/resources/sqlite/dataBaseSchema.sqlite3",
                "jdbc:sqlite:src/main/resources/sqlite/appDb.sqlite3", "src/main/resources/csv/component.csv",
                "src/main/resources/csv/foodname_FI.csv", "src/main/resources/csv/component_value.csv");
        fis = FoodItemStructure.getFoodItemStructure(dataBaseName);
        ncs = NutritionalComponentStructure.getNutrititonalComponentStructure(dataBaseName);
        newMeal = null;
        user = null;
    }

    public static void init(String dataBaseName) {
        if (singleton == null) {
            singleton = new WellnessService(dataBaseName);
        }
    }

    public static WellnessService getInstance() {
        return singleton;
    }

    public void createNewUser(String username, String password) {
        User userN = new User(username, User.md5Hash(password));
        UserDao userDao = new UserDaoSqlite3(this.getDataBaseName());
        userDao.addUser(userN);
        this.setUser(userN);
    }

    public boolean login(String username, String password) {
        UserDao userDao = new UserDaoSqlite3(this.getDataBaseName());
        User userN = userDao.getUser(username, User.md5Hash(password));
        if (userN != null) {
            this.setUser(userN);
            return true;
        } else {
            return false;
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setDataBaseName(String name) {
        this.dataBaseName = name;
    }

    public String getDataBaseName() {
        return this.dataBaseName;
    }

    public void setNewMeal(Meal meal) {
        this.newMeal = meal;
    }

    public Meal getNewMeal() {
        return this.newMeal;
    }

    public void addFoodToNewMeal(FoodItem fi, Double amount) {
        this.newMeal.addFoodItem(fi, amount);
    }

    public void removeFoodItemFromNewMeal(String foodname) {
        this.newMeal.removeFoodItem(fis.getFoodItemByName(foodname));
    }

    public void addNewMealToUser() {
        if (this.newMeal != null) {
            this.user.addMeal(this.newMeal);
            MealDao md = new MealDaoSqlite3(this.getDataBaseName());
            md.addOne(newMeal);
            this.newMeal = null;
        }
    }

    public FoodItemStructure getFis() {
        return this.fis;
    }

}
