/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItem;
import fi.otm.wellnessapp.structure.FoodItemStructure;
import fi.otm.wellnessapp.structure.Meal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vili
 */
public class MealDaoSqlite3 implements MealDao {

    private String filename;
    private Sqlite3ConnectionManager scm;

    public MealDaoSqlite3(String filename) {
        this.filename = filename;
        this.scm = Sqlite3ConnectionManager.getConnectionManager(filename);
    }

    private void resolveFoodItems(ArrayList<Meal> mealList) {
        mealList.stream().forEach((Meal meal) -> {
            this.resolveFoodItems(meal);
        });
    }

    private void resolveFoodItems(Meal meal) {
        String sqlQuery = "SELECT * FROM InAMeal WHERE MealID = (?);";
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(this.filename);
        try {
            PreparedStatement prep;
            prep = scm.connect().prepareStatement(sqlQuery);
            prep.setInt(1, meal.getMealId());
            ResultSet rsInner = prep.executeQuery();
            while (rsInner.next()) {
                meal.addFoodItem(fis.getFoodItemById(rsInner.getInt("FoodID")),
                        rsInner.getDouble("Amount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Meal> getAll() {
        String sqlQuery = "SELECT * FROM Meal;";

        ArrayList<Meal> mealList = new ArrayList<>();
        try {
            Statement mealStatement = scm.connect().createStatement();
            ResultSet rs = mealStatement.executeQuery(sqlQuery);
            while (rs.next()) {
                Meal m = new Meal(new Date(rs.getTimestamp("Time").getTime()), rs.getInt("UserID"));
                m.setMealId(rs.getInt("MealID"));
                mealList.add(m);
            }
            this.resolveFoodItems(mealList);
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mealList;
    }

    @Override
    public ArrayList<Meal> getByUserId(int userid) {
        ArrayList<Meal> mealList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM MEAL "
                + "WHERE UserID = (?);";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlQuery);
            prep.setInt(1, userid);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                Meal m = new Meal(new Date(rs.getDate("Time").getTime()), rs.getInt("UserID"));
                m.setMealId(rs.getInt("MealID"));
                mealList.add(m);
            }
            this.resolveFoodItems(mealList);
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mealList;
    }

    @Override
    public Meal getOne(int mealId) {
        Meal meal = null;
        String sqlQuery = "SELECT * FROM MEAL WHERE MealID = (?);";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlQuery);
            prep.setInt(1, mealId);
            ResultSet rs = prep.executeQuery();
            rs.next();
            if (rs.isAfterLast()) {
                return null; // database did not contain id
            }
            meal = new Meal(new Date(rs.getTimestamp("Time").getTime()),
                    rs.getInt("UserID"));
            meal.setMealId(rs.getInt("MealID"));
            this.resolveFoodItems(meal);
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meal;
    }

    private void addCore(Meal meal) throws SQLException {
        String sqlInsert = "INSERT INTO MEAL (UserID, Time) VALUES (?,?)";
        PreparedStatement prep = scm.connect().prepareStatement(sqlInsert);
        prep.setInt(1, meal.getUserId());
        prep.setTimestamp(2, new java.sql.Timestamp(meal.getTime().getTime()));
        prep.executeUpdate();
        String idQuery = "SELECT last_insert_rowid()";
        Statement stmnt = scm.connect().createStatement();
        ResultSet rs = stmnt.executeQuery(idQuery);
        meal.setMealId(rs.getInt(1));
        this.addMealFoodRelation(meal);
    }

    private void addMealFoodRelation(Meal meal) {
        String sqlInsertInAMeal = "INSERT INTO InAMeal (MealID, FoodID, Amount)"
                + "VALUES (?,?,?)";
        meal.getFoodItems().entrySet().forEach((Entry<FoodItem, Double> e) -> {
            try {
                PreparedStatement prep = scm.connect().prepareStatement(sqlInsertInAMeal);
                prep.setInt(1, meal.getMealId());
                prep.setInt(2, e.getKey().getId());
                prep.setString(3, e.getValue().toString());
                prep.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    /**
     * Adds one meal to database in one transaction
     * @param meal  Meal to be added
     */

    @Override
    public void addOne(Meal meal) {
        try {
            scm.connect();
            this.addCore(meal);
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(Meal meal) {
        String sqlDelete = "DELETE FROM Meal "
                + "WHERE MealID = (?)";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlDelete);
            prep.setInt(1, meal.getMealId());
            prep.executeUpdate();
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addAll(List<Meal> mealList) {
        mealList.stream().forEach((Meal meal) -> {
            try {
                this.addCore(meal);
            } catch (SQLException ex) {
                Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(MealDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
