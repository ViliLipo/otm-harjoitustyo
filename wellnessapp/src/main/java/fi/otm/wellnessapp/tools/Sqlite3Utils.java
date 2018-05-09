/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author vili
 */
public class Sqlite3Utils {

    private String schemaFile;
    private String dataBaseFile;
    
    /**
     * Sets up schema for database
     * @param filename file where the schema is.
     * @param dbname file path to database file
     */

    public void setupSchema(String filename, String dbname) {
        try {
            try {
                String schema = readFile(filename);
                Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
                Statement stmnt = conn.createStatement();
                stmnt.executeUpdate(schema);
                conn.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readFile(String filename) throws FileNotFoundException {
        try {
            InputStreamReader fr = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename), "utf-8");
            BufferedReader br = new BufferedReader(fr);
            String value = br.lines().collect(Collectors.joining("\n"));
            br.close();
            fr.close();
            return value;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Initialises the database if needed.
     * @param schemafile file where the schema is
     * @param dbname file where the database is or will be
     * @param dbFolderPath folder where the database resides
     * @param compCsv .csv file for NutritionalComponents
     * @param foodCsv .csv file for FoodItems
     * @param compValCsv .csv file for ComponentValues.
     */

    public void initDb(String schemafile, String dbname, String dbFolderPath, String compCsv, String foodCsv, String compValCsv) {
        String query = "SELECT * FROM Component";
        try {
            new File(dbFolderPath).mkdirs();
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
            ResultSet rs = conn.createStatement().executeQuery(query);
            if (!rs.next()) { // empty db
                setupSchema(schemafile, dbname);
                CsvParser cp = new CsvParser();
                cp.convertComponentCSVtoSQLITE(compCsv, dbname);
                cp.convertFoodItems(compValCsv, foodCsv, dbname);
            }
            conn.close();
        } catch (SQLException ex) { // no schema at all
            setupSchema(schemafile, dbname);
            CsvParser cp = new CsvParser();
            cp.convertComponentCSVtoSQLITE(compCsv, dbname);
            cp.convertFoodItems(compValCsv, foodCsv, dbname);
        }
    }

}
