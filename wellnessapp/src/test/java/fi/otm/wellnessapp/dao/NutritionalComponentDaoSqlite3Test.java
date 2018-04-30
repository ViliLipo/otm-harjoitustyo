/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vili
 */
public class NutritionalComponentDaoSqlite3Test {

    private final String testDbName = "db/testDb.sqlite3";

    public NutritionalComponentDaoSqlite3Test() {

    }

    @Before
    public void setUp() {
        File file = new File(testDbName);
        file.delete();
        Sqlite3ConnectionManager.reset();
        Sqlite3Utils s3u = new Sqlite3Utils();
        s3u.setupSchema("sqlite/dataBaseSchema.sqlite3", (testDbName));
        String insert = "INSERT INTO Component "
                + "VALUES(?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            PreparedStatement prep = conn.prepareStatement(insert);
            prep.setString(1, "TEST");
            prep.setString(2, "Unit");
            prep.setString(3, "Class");
            prep.setString(4, "Classp");
            prep.executeUpdate();
            prep.setString(1, "TEST2");
            prep.setString(2, "Unit");
            prep.setString(3, "Class");
            prep.setString(4, "Classp");
            prep.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NutritionalComponentDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @After
    public void tearDown() {
        File file = new File(testDbName);
        file.delete();
    }

    /**
     * Test of getAll method, of class NutritionalComponentDaoSqlite3.
     */
    @Test
    public void testGetAll() {

        System.out.println("getAll");
        NutritionalComponentDaoSqlite3 instance = new NutritionalComponentDaoSqlite3(testDbName);
        ArrayList<NutritionalComponent> result = instance.getAll();
        //System.out.println(result);
        assertEquals("TEST", result.get(0).getName());
        assertEquals("TEST2", result.get(1).getName());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getOne method, of class NutritionalComponentDaoSqlite3.
     */
    @Test
    public void testGetOne() {
        System.out.println("getOne");
        NutritionalComponentDaoSqlite3 instance = new NutritionalComponentDaoSqlite3(testDbName);
        NutritionalComponent result = instance.getOne("TEST2");
        assertEquals("TEST2", result.getName());
        assertEquals("Unit", result.getUnit());
        assertEquals("Class", result.getCmpClass());
        assertEquals("Classp", result.getCmpClassp());
    }

    /**
     * Test of add method, of class NutritionalComponentDaoSqlite3.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        NutritionalComponent nc = new NutritionalComponent("ADD", "kg", "img", "not real");
        NutritionalComponentDaoSqlite3 instance = new NutritionalComponentDaoSqlite3(testDbName);
        instance.add(nc);
        try {
            // TODO review the generated test code and remove the default call to fail.
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            String query = "SELECT * FROM Component "
                    + " WHERE EUFDNAME = (?)";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, "ADD");
            ResultSet rs = prep.executeQuery();
            assertEquals("ADD", rs.getString("EUFDNAME"));
            assertEquals("kg", rs.getString("COMPUNIT"));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NutritionalComponentDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of remove method, of class NutritionalComponentDaoSqlite3.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        String eufdName = "TEST2";
        NutritionalComponentDaoSqlite3 instance = new NutritionalComponentDaoSqlite3(testDbName);
        instance.remove(eufdName);
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + testDbName);
            String query = "SELECT * FROM Component "
                    + " WHERE EUFDNAME = (?)";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, eufdName);
            ResultSet rs = prep.executeQuery();
            rs.next();
            rs.next();
            assertEquals(true, rs.isAfterLast());
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NutritionalComponentDaoSqlite3Test.class.getName()).log(Level.SEVERE, null, ex);
            fail("Sqlite exception");

        }

    }

}
