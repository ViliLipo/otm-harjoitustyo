/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public static void setupSchema(String filename, String dbname) {
        try {
            try {
                String schema = readFile(filename);
                Connection conn = DriverManager.getConnection(dbname);
                Statement stmnt = conn.createStatement();
                stmnt.executeUpdate(schema);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String readFile(String filename) throws FileNotFoundException {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        return br.lines().collect(Collectors.joining("\n"));
    }

    public static void initDb(String schemafile, String dbname, String compCsv, String foodCsv, String compValCsv) {
        String query = "SELECT * FROM Component";
        try {
            Connection conn = DriverManager.getConnection(dbname);
            ResultSet rs = conn.createStatement().executeQuery(query);
            if (!rs.next()) { // empty db
                setupSchema(schemafile, dbname);
                CsvParser.convertComponentCSVtoSQLITE(compCsv, dbname);
                CsvParser.convertFoodItems(compValCsv, foodCsv, dbname);
            }
        } catch (SQLException ex) { // no schema at all
            setupSchema(schemafile, dbname);
            CsvParser.convertComponentCSVtoSQLITE(compCsv, dbname);
            CsvParser.convertFoodItems(compValCsv, foodCsv, dbname);
        }
    }

}
