/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.tools;

import fi.otm.wellnessapp.dao.FoodItemDaoSqlite3;
import fi.otm.wellnessapp.dao.NutritionalComponentDaoSqlite3;
import fi.otm.wellnessapp.dao.NutritionalComponentStructure;
import fi.otm.wellnessapp.structure.FoodItem;
import fi.otm.wellnessapp.structure.NutritionalComponent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vili
 */
public class CsvParser {
    
    public static ArrayList<String> lines(String filename) throws FileNotFoundException {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> lines = new ArrayList<>();
        br.lines().forEach(line -> {
            lines.add(line);
        });
        return lines;
    }
    
    public static ArrayList<NutritionalComponent> parseComponents(String filename) {
        try {
            ArrayList<String> lines = lines(filename);
            Iterator<String> iterator = lines.iterator();
            iterator.next(); // firstLine is a header we dont need
            String line;
            ArrayList<NutritionalComponent> ncList = new ArrayList<>();
            while(iterator.hasNext()) {
                line = iterator.next();
                String[] pieces = line.split(";") ;
                ncList.add(new NutritionalComponent(
                pieces[0],pieces[1], pieces[2], pieces[3]));
                
            }
            return ncList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static void convertComponentCSVtoSQLITE(String filename, String dbname) {
        ArrayList<NutritionalComponent> ncList = parseComponents(filename);
        NutritionalComponentDaoSqlite3 ncDao = new NutritionalComponentDaoSqlite3(dbname);
        for(NutritionalComponent nc : ncList) {
            ncDao.add(nc);
        }
    }
    
    public static ArrayList<FoodItem> parseItems(String filename) {
        ArrayList<FoodItem> fiList = new ArrayList<>();
        try {
            ArrayList<String> lines = lines(filename);
            lines.remove(0); // this is the header we wont need it
            lines.stream().forEach(line -> {
                String[] pieces = line.split(";");
                fiList.add(new FoodItem(Integer.parseInt(pieces[0]),
                        pieces[1],
                        pieces[2]));
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fiList;
    }
    public static void resolveNutritionFoodRelations(String componentValueFile, String dataBaseName, ArrayList<FoodItem> fiList) {
        HashMap<Integer, FoodItem> fiMap = new HashMap<>();
        fiList.stream().forEach(foodItem -> {
            fiMap.put(foodItem.getId(), foodItem);
        });
        try {
            ArrayList<String> lines = lines(componentValueFile);
            NutritionalComponentStructure ncs =
                    NutritionalComponentStructure
                            .getNutrititonalComponentStructure(dataBaseName);
            lines.remove(0); // This is the header, we wont need it
            lines.stream().forEach(line -> {
                if(!line.contentEquals("")) {
                    String[] pieces = line.split(";");
                    int id = Integer.parseInt(pieces[0]);
                    //System.out.println("id :" + id);
                    String eufdname = pieces[1];
                    //System.out.println(eufdname);
                    pieces[2] = pieces[2].replace(",", ".");
                    //System.out.println(pieces[2]);
                    Double amount;
                    try {
                        amount = Double.parseDouble(pieces[2]);
                    } catch (Exception ex) {
                        amount = 0.d;
                    }
                    FoodItem fi = fiMap.get(id);
                    fi.addContents(amount, ncs.getNutCompByName(eufdname));
                }

            });
            System.out.println("DONE");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    * In order for this to work components must be
    * written to the db first
    */
    public static void convertFoodItems(String componentValueFile, String foodItemFile, String dbName) {
        ArrayList<FoodItem>  fiList = parseItems(foodItemFile);
        resolveNutritionFoodRelations(componentValueFile, dbName, fiList);
        FoodItemDaoSqlite3 fid = new FoodItemDaoSqlite3(dbName);
        fid.addAll(fiList);
        
    }
}
