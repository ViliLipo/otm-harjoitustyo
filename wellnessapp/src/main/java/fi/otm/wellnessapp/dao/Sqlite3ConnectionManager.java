/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author vili
 */
public class Sqlite3ConnectionManager {
    
    private static Sqlite3ConnectionManager singleton = null;
    
    private Connection conn = null;
    private String dbName;
    private SQLiteConfig config;
    
    private Sqlite3ConnectionManager(String dbName) {
        this.dbName = dbName;
        this.config = new SQLiteConfig();
        this.config.enforceForeignKeys(true);
    }
    public static Sqlite3ConnectionManager getConnectionManager(String dbName) {
        if (singleton == null) {
            singleton = new Sqlite3ConnectionManager(dbName);
        }
        return singleton;
    }
    public static Sqlite3ConnectionManager getConnectionManager() {
        if (singleton == null) {
            System.out.println("CALL WITH FILENAME FIRST");
        }
        return singleton;
    }
    
    public static void reset() {
        singleton = null;
    }
    
    public void setDbName(String filename) {
        this.dbName = filename;
    }
    
    public Connection connect() throws SQLException {
        if ((this.conn == null) || this.conn.isClosed()) {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbName, config.toProperties());
            conn.setAutoCommit(false);
        }
        return this.conn;
    }
    
}
