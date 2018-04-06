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
            String schema = null;
            try {
                FileReader fr = new FileReader(filename);
                BufferedReader br = new BufferedReader(fr);
                schema = br.lines().collect(Collectors.joining("\n"));
                System.out.println("DEBUG");
                System.out.print(schema);
                System.out.println("\n DEBUG");
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection conn = DriverManager.getConnection(dbname);
            Statement stmnt = conn.createStatement();
            stmnt.executeUpdate(schema);
            System.out.println(conn.getSchema());
            
        } catch (SQLException ex) {
            Logger.getLogger(Sqlite3Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void initDb() {
        System.out.println("this is indeed a quite stupid dummy why dont"
                + "you fix it while you are at it");
    }
    
}
