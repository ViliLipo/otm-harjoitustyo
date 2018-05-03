/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItem;
import fi.otm.wellnessapp.structure.NutritionalComponent;
import fi.otm.wellnessapp.tools.Sqlite3Utils;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author vili
 */
public class FoodItemDaoSqlite3Test {

    private final String testDbName = "db/testDb.sqlite3";
    
    private final String dbPath = "db/";

    private NutritionalComponent energy;

    public FoodItemDaoSqlite3Test() {
    }

    @Before
    public void setUp() {
        File file = new File(testDbName);
        file.delete();
        file = new File(dbPath);
        file.mkdirs();
        Sqlite3ConnectionManager.reset();
        Sqlite3Utils s3u = new Sqlite3Utils();
        s3u.setupSchema("sqlite/dataBaseSchema.sqlite3", (testDbName));
        String insert = "INSERT INTO Component "
                + "VALUES(?,?,?,?)";
        String insert2 = "INSERT OR IGNORE INTO ComponentValue VALUES(?,?,?)";
        String insert3 = "INSERT OR IGNORE INTO FoodNameFi VALUES(?,?,?)";

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            PreparedStatement prep = conn.prepareStatement(insert);
            prep.setString(1, "ENERC");
            prep.setString(2, "kj");
            prep.setString(3, "ENER");
            prep.setString(4, "ENER");
            prep.executeUpdate();
            prep = conn.prepareStatement(insert2);
            prep.setInt(1, 1);
            prep.setString(2, "ENERC");
            prep.setString(3, "400.00");
            prep.executeUpdate();
            prep = conn.prepareStatement(insert2);
            prep.setInt(1, 2);
            prep.setString(2, "ENERC");
            prep.setString(3, "600.00");
            prep.executeUpdate();
            prep = conn.prepareStatement(insert3);
            prep.setInt(1, 1);
            prep.setString(2, "lihapulla");
            prep.setString(3, "fi");
            prep.executeUpdate();
            prep = conn.prepareStatement(insert3);
            prep.setInt(1, 2);
            prep.setString(2, "pulla");
            prep.setString(3, "fi");
            prep.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Broken setup");
        }
        energy = new NutritionalComponent("ENERC", "kj", "ENER", "ENER");
    }

    @After
    public void tearDown() {
        File file = new File(testDbName);
        file.delete();
    }

    /**
     * Test of getAll method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testGetAll() {
        //System.out.println("getAll");
        FoodItemDaoSqlite3 instance = new FoodItemDaoSqlite3(testDbName);
        FoodItem sweetroll = new FoodItem(2, "pulla", "fi");
        NutritionalComponent nc = new NutritionalComponent("ENERC", "kj", "ENER", "ENER");
        sweetroll.addContents(600.00, nc);
        FoodItem meatBall = new FoodItem(1, "lihapulla", "fi");
        meatBall.addContents(400.00, nc);
        ArrayList<FoodItem> result = instance.getAll();
        assertEquals(meatBall.getName(), result.get(0).getName());
        assertEquals(sweetroll.getName(), result.get(1).getName());
        assertEquals(meatBall.getContents().get(nc), result.get(0).getContents().get(nc));
    }

    /**
     * Test of getOne method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testGetOne() {
        //System.out.println("getOne");
        FoodItemDaoSqlite3 instance = new FoodItemDaoSqlite3(testDbName);
        FoodItem result = instance.getOne(1);
        assertEquals("lihapulla", result.getName());
        double d = result.getContents().get(new NutritionalComponent("ENERC", "kj", "ENER", "ENER"));
        assertEquals(400.00, d, 0.00001);
    }

    /**
     * Test of addOne method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testAddOne() {
        //System.out.println("addOne");
        FoodItem fi = new FoodItem(3, "Rinkeli", "fi");
        fi.addContents(300.0d, energy);
        FoodItemDaoSqlite3 instance = new FoodItemDaoSqlite3(testDbName);
        instance.addOne(fi);
        String query = "SELECT * FROM FoodNameFi WHERE FoodID = (?)";
        String query2 = "SELECT * FROM ComponentValue WHERE FoodID = (?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setInt(1, 3);
            ResultSet rs = prep.executeQuery();
            rs.next();
            assertTrue(rs.getString("FoodName").contentEquals("Rinkeli"));
            prep = conn.prepareStatement(query2);
            prep.setInt(1, 3);
            rs = prep.executeQuery();
            rs.next();
            assertEquals("300.0", rs.getString("BESTLOC"));
            conn.close();

        } catch (SQLException ex) {
            fail("exception");
            Logger.getLogger(FoodItemDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of addAll method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testAddAll() {
        //System.out.println("addAll");
        FoodItem fi = new FoodItem(3, "Rinkeli", "fi");
        fi.addContents(300.0d, energy);
        FoodItemDaoSqlite3 instance = new FoodItemDaoSqlite3(testDbName);
        FoodItem fi2 = new FoodItem(4, "muna", "fi");
        String query = "SELECT * FROM FoodNameFi";
        List<FoodItem> fiList = new ArrayList<>();
        fiList.add(fi);
        fiList.add(fi2);
        instance.addAll(fiList);
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            HashSet<String> stringSet = new HashSet<>();
            while (rs.next()) {
                stringSet.add(rs.getString("FoodName"));
            }
            assertTrue(stringSet.contains("Rinkeli"));
            assertTrue(stringSet.contains("muna"));
            conn.close();
        } catch (SQLException ex) {
            fail("Exception");
            Logger.getLogger(FoodItemDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of remove method, of class FoodItemDaoSqlite3.
     */
    @Test
    public void testRemove() {
        //System.out.println("remove");
        FoodItem meatBall = new FoodItem(1, "lihapulla", "fi");
        FoodItemDaoSqlite3 instance = new FoodItemDaoSqlite3(testDbName);
        instance.remove(meatBall);
        String query = "SELECT * FROM FoodNameFi";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            HashSet<String> stringSet = new HashSet<>();
            while (rs.next()) {
                stringSet.add(rs.getString("FoodName"));
            }
            assertFalse(stringSet.contains("lihapulla"));
            conn.close();
        } catch (SQLException ex) {
            fail("exception");
            Logger.getLogger(FoodItemDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
