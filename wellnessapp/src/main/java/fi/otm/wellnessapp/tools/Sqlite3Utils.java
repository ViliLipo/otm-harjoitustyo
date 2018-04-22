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
        InputStreamReader fr = null;
        try {
            fr = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename), "utf-8");
            BufferedReader br = new BufferedReader(fr);
            String value = br.lines().collect(Collectors.joining("\n"));
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return value;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public void initDb(String schemafile, String dbname, String compCsv, String foodCsv, String compValCsv) {
        String query = "SELECT * FROM Component";
        try {
            File f = new File("db/");
            f.mkdirs();
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
