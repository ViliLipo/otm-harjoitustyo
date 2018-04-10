/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vili
 */
public class Sqlite3ConnectionManager {
    
    private static Sqlite3ConnectionManager SINGLETON = null;
    
    private Connection conn = null;
    private String dbName;
    private Sqlite3ConnectionManager(String dbName) {
        this.dbName = dbName;
    }
    public static Sqlite3ConnectionManager getConnectionManager(String dbName) {
        if(SINGLETON == null) {
            SINGLETON = new Sqlite3ConnectionManager(dbName);
        }
        return SINGLETON;
    }
    public static Sqlite3ConnectionManager getConnectionManager() {
        if(SINGLETON == null) {
            System.out.println("CALL WITH FILENAME FIRST");
        }
        return SINGLETON;
    }
    
    public void setDbName(String filename) {
        this.dbName = filename;
    }
    
    public Connection connect() throws SQLException {
        if ((this.conn == null) || this.conn.isClosed()) {
            this.conn = DriverManager.getConnection(this.dbName);
            conn.setAutoCommit(false);
        }
        return this.conn;
    }
    
}