/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.FoodItem;
import fi.otm.wellnessapp.structure.NutritionalComponent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vili
 */
public class FoodItemDaoSqlite3 implements FoodItemDao {

    private String filename;
    private Sqlite3ConnectionManager scm;

    public FoodItemDaoSqlite3(String filename) {
        this.filename = filename;
        this.scm = Sqlite3ConnectionManager.getConnectionManager(filename);
    }

    private void resolveComponents(FoodItem fi) {
        try {
            NutritionalComponentStructure ncs = NutritionalComponentStructure
                    .getNutrititonalComponentStructure(this.filename);
            String sqlQuery = "SELECT EUFDNAME, BESTLOC FROM ComponentValue  WHERE FoodID = (?);";
            PreparedStatement prep = scm.connect().prepareStatement(sqlQuery);
            prep.setInt(1, fi.getId());
            ResultSet rsInner = prep.executeQuery();
            while (rsInner.next()) {
                String amountString = rsInner.getString("BESTLOC");
                String name = rsInner.getString("EUFDNAME");
                Double amount = Double.parseDouble(amountString);
                fi.addContents(amount, ncs.getNutCompByName(name));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<FoodItem> getAll() {
        ArrayList<FoodItem> fiList = new ArrayList<>();
        String sqlQuery = "SELECT FoodID, FoodName, Lang  FROM FoodNameFi; ";
        try {
            ResultSet rs = scm.connect().createStatement().executeQuery(sqlQuery);
            while (rs.next()) {
                fiList.add(new FoodItem(
                        rs.getInt("FoodID"), rs.getString("FoodName"),
                        rs.getString("Lang")
                ));
            }
            fiList.stream().forEach((FoodItem fi) -> this.resolveComponents(fi));
            scm.connect().close();
            return fiList;
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public FoodItem getOne(int foodItemID) {
        String sqlQuery = "SELECT FoodID, FoodName, Lang FROM FoodNameFi WHERE FoodID = (?); ";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlQuery);
            prep.setInt(1, foodItemID);
            ResultSet rs = prep.executeQuery();
            FoodItem value = null;
            if (rs.next()) {
                value = new FoodItem(rs.getInt("FoodID"), rs.getString("FoodName"), rs.getString("Lang"));
                this.resolveComponents(value);
            }
            scm.connect().close();
            return value;
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void addComponentRelations(FoodItem fi) {
        String sqlInsert2 = "INSERT OR IGNORE INTO ComponentValue (FoodId, EUFDNAME, BESTLOC)"
                + "VALUES(?,?,?);";
        HashMap<NutritionalComponent, Double> contents = fi.getContents();
        contents.keySet().parallelStream().forEach(k -> {
            try {
                PreparedStatement prep2 = scm.connect().prepareStatement(sqlInsert2);
                Double amount = contents.get(k);
                String amountString = amount.toString();
                prep2.setInt(1, fi.getId());
                prep2.setString(2, k.getName());
                prep2.setString(3, amountString);
                prep2.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addCore(FoodItem fi) throws SQLException {
        String sqlInsert = "INSERT OR IGNORE INTO FoodNameFi (FoodID, FoodName, Lang)"
                + "VALUES(?,?,?)";
        PreparedStatement prep = scm.connect().prepareStatement(sqlInsert);
        prep.setInt(1, fi.getId());
        prep.setString(2, fi.getName());
        prep.setString(3, fi.getLanguage());
        prep.executeUpdate();
        addComponentRelations(fi);
    }

    @Override
    public void addOne(FoodItem fi) {
        try {
            this.addCore(fi);
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addAll(List<FoodItem> fiList) {
        try {
            fiList.stream().forEach(fi -> {
                try {
                    this.addCore(fi);
                } catch (SQLException ex) {
                    Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(FoodItem fi) {
        String sqlDelete = "DELETE FROM FoodNameFi "
                + "WHERE FoodID = (?)";
        try {
            PreparedStatement prep = scm.connect().prepareStatement(sqlDelete);
            prep.setInt(1, fi.getId());
            prep.executeUpdate();
            scm.connect().commit();
            scm.connect().close();
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemDaoSqlite3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
