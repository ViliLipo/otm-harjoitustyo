/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.Meal;
import fi.otm.wellnessapp.structure.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vili
 */
public class UserDaoSqlite3 implements UserDao {

    private String dbname;
    private Sqlite3ConnectionManager scm;

    public UserDaoSqlite3(String dbname) {
        this.dbname = dbname;
        this.scm = Sqlite3ConnectionManager.getConnectionManager(dbname);
    }

    @Override
    public User getUser(String username, String passwordHash) {
        String sqlQuery = "SELECT * FROM User WHERE UserName = (?) AND PasswordHash = (?)";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlQuery);
            prep.setString(1, username);
            prep.setString(2, passwordHash);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("UserName"), rs.getString("PasswordHash"));
                user.setDailyKiloJouleGoal(rs.getDouble("DailyKiloJouleGoal"));
                user.setUserID(rs.getInt("UserID"));
                ArrayList<Meal> mealList = new MealDaoSqlite3(dbname).getByUserId(user.getUserId());
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        String sqlInsert = "INSERT INTO User (UserName, PasswordHash,"
                + " DailyKilojouleGoal) VALUES(?,?,?);";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlInsert);
            prep.setString(1, user.getUserName());
            prep.setString(2, user.getPasswordHash());
            prep.setDouble(3, user.getDailyKiloJouleGoal());
            prep.executeUpdate();
            String query = "SELECT last_insert_rowid()";
            Statement stmnt = scm.connect().createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            user.setUserID(rs.getInt(1));
            System.out.println("Userid = " + user.getUserId());
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void removeUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
