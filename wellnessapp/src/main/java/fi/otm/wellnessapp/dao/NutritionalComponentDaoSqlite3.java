/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.NutritionalComponent;
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
public class NutritionalComponentDaoSqlite3 implements NutritionalComponentDao {

    private final String filename;

    private Sqlite3ConnectionManager scm;

    public NutritionalComponentDaoSqlite3(String filename) {
        this.filename = filename;
        Sqlite3ConnectionManager.reset();
        this.scm = Sqlite3ConnectionManager.getConnectionManager(filename);
    }

    @Override
    public ArrayList<NutritionalComponent> getAll() {
        try {
            ArrayList<NutritionalComponent> componentList = new ArrayList<>();
            String query = "SELECT * FROM COMPONENT";
            ResultSet rs = scm.connect().createStatement().executeQuery(query);
            while (rs.next()) {
                componentList.add(new NutritionalComponent(
                        rs.getString("EUFDNAME"), //NAME
                        rs.getString("COMPUNIT"), //UNIT
                        rs.getString("CMPCLASS"),
                        rs.getString("CMPCLASSP")
                ));
            }
            scm.connect().close();
            return componentList;
        } catch (SQLException ex) {
            Logger.getLogger(NutritionalComponentDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public NutritionalComponent getOne(String eufdName) {
        try {
            String query = "SELECT * FROM Component WHERE EUFDNAME = ?;";
            PreparedStatement prep = scm.connect().prepareStatement(query);
            prep.setString(1, eufdName);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                NutritionalComponent nc = new NutritionalComponent(rs.getString("EUFDNAME"),
                        rs.getString("COMPUNIT"), rs.getString("CMPCLASS"),
                        rs.getString("CMPCLASSP")
                );
                scm.connect().close();
                return nc;
            } else {
                scm.connect().close();
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public void add(NutritionalComponent nc) {
        try {
            String sqlInsert = "INSERT OR IGNORE INTO COMPONENT"
                    + "(EUFDNAME, COMPUNIT, CMPCLASS, CMPCLASSP)"
                    + "VALUES (?,?,?,?)";
            PreparedStatement prep = scm.connect().prepareStatement(sqlInsert);
            prep.setString(1, nc.getName());
            prep.setString(2, nc.getUnit());
            prep.setString(3, nc.getCmpClass());
            prep.setString(4, nc.getCmpClassp());
            prep.executeUpdate();
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(String eufdName) {
        try {
            String sqlDelete = "DELETE FROM COMPONENT "
                    + " WHERE EUFDNAME = (?);";
            PreparedStatement prep = scm.connect().prepareStatement(sqlDelete);
            prep.setString(1, eufdName);
            prep.executeUpdate();
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(NutritionalComponentDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
