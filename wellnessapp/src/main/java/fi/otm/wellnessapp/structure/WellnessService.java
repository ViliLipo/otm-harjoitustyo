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
 * Service providing needed functionality to user interface.
 *
 * @author Vili Lipo, vili.lipo@helsinki.fi
 */
public class WellnessService {

    private static WellnessService singleton;
    private User user;
    private final String dataBaseName = System.getProperty("user.home") + "/.WellnessApp/db/appDb.sqlite3";
    private final String folderPath = System.getProperty("user.home") + "/.WellnessApp/db/";
    private FoodItemStructure fis;
    private NutritionalComponentStructure ncs;
    private Meal newMeal;

    private WellnessService() {
        Sqlite3Utils s3u = new Sqlite3Utils();
        s3u.initDb("sqlite/dataBaseSchema.sqlite3",
                dataBaseName, folderPath, "csv/component.csv",
                "csv/foodname_FI.csv", "csv/component_value.csv");
        fis = FoodItemStructure.getFoodItemStructure(dataBaseName);
        ncs = NutritionalComponentStructure.getNutrititonalComponentStructure(dataBaseName);
        newMeal = null;
        user = null;
    }

    public static WellnessService getInstance() {
        if (singleton == null) {
            singleton = new WellnessService();
        }
        return singleton;
    }

    /**
     * Creates new user and stores it to database
     *
     * @param username Name of new user
     * @param password Password of new user
     *
     */
    public void createNewUser(String username, String password) {
        User userN = new User(username, User.md5Hash(password));
        UserDao userDao = new UserDaoSqlite3(this.getDataBaseName());
        userDao.addUser(userN);
        this.setUser(userN);
    }

    /**
     * Logins existing user
     *
     * @param username Name of the user
     * @param password Password of the user
     * @return success of this login
     * @see fi.otm.wellnessapp.dao.UserDaoSqlite3#getUser(java.lang.String,
     * java.lang.String)
     */
    public boolean login(String username, String password) {
        UserDao userDao = new UserDaoSqlite3(this.getDataBaseName());
        User userN = new User(username, User.md5Hash(password));
        userN = userDao.getUser(userN.getUserName(), userN.getPasswordHash());
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

    /**
     * adds this.new meal to this.user and writes the meal to database
     *
     * @see
     * fi.otm.wellnessapp.dao.MealDaoSqlite3#addOne(fi.otm.wellnessapp.structure.Meal)
     */
    public void addNewMealToUser() {
        if ((this.newMeal != null) && (this.newMeal.getFoodItems().size() > 0)) {
            this.user.addMeal(this.newMeal);
            MealDao md = new MealDaoSqlite3(this.getDataBaseName());
            md.addOne(newMeal);
        }
        this.newMeal = null;
    }

    public FoodItemStructure getFis() {
        return this.fis;
    }

    /**
     * Updates users calorie goal and stores it to Database
     *
     * @param goal new calorie goal
     */
    public void updateUserCalorieGoal(int goal) {
        this.user.setCalorieGoal(goal);
        UserDao ud = new UserDaoSqlite3(this.getDataBaseName());
        ud.updateUser(user);
    }

    /**
     * Updates users password to db if it matches confirmation
     *
     * @param password new Password
     * @param confirmation new Password again
     * @return is password and confirmation a match
     */
    public boolean updateUserPassword(String password, String confirmation) {
        if (password.contentEquals(confirmation)) {
            this.user.setPassword(password);
            UserDao ud = new UserDaoSqlite3(this.getDataBaseName());
            ud.updateUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes meal from user and removes meal from database
     *
     * @param meal Meal to be removed
     * @see
     * fi.otm.wellnessapp.dao.MealDaoSqlite3#remove(fi.otm.wellnessapp.structure.Meal)
     */
    public void removeMeal(Meal meal) {
        this.user.getMealList().remove(meal);
        MealDao md = new MealDaoSqlite3(this.dataBaseName);
        md.remove(meal);
    }

}
