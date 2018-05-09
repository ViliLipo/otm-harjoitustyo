/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for managing user and its data
 *
 * @author vili
 */
public class User {

    private String userName;
    private String passwordHash;
    private int userId;

    private int dailyCalorieGoal;
    private double dailyProteinGoal;

    private ArrayList<Meal> mealList;

    /**
     *
     * @param userName Name of this user
     * @param passwordHash Hashed password, use User.md5Hash(String string)
     */
    public User(String userName, String passwordHash) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.mealList = new ArrayList<>();
        this.dailyCalorieGoal = 2000;
    }

    public void setUserID(int id) {
        this.userId = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setPassword(String password) {
        this.passwordHash = User.md5Hash(password);
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setCalorieGoal(int goal) {
        this.dailyCalorieGoal = goal;
    }

    public int getDailyCalorieGoal() {
        return this.dailyCalorieGoal;
    }

    /**
     * Sets users calorie goal with conversion from kilojoule.
     *
     * @param value double in kilojoule
     */
    public void setDailyKiloJouleGoal(double value) {
        this.setCalorieGoal((int) Math.floor((value / 4.1868) + 0.5d));
    }

    /**
     * Users calorie goal converted to kilojoule
     *
     * @return double in kilojoule,
     */
    public int getDailyKiloJouleGoal() {
        return ((int) Math.floor((this.dailyCalorieGoal * 4.1868) + 0.5d));
    }

    public double getDailyProteinGoal() {
        return this.dailyProteinGoal;
    }

    public void setDailyProteinGoal(double value) {
        this.dailyProteinGoal = value;
    }

    public void setMealList(ArrayList<Meal> list) {
        this.mealList = list;
    }

    /**
     *
     * @return meals in time order newest first
     */
    public ArrayList<Meal> getMealList() {
        Collections.sort(mealList);
        Collections.reverse(mealList);
        return this.mealList;
    }

    public void addMeal(Meal meal) {
        this.mealList.add(meal);
    }

    /**
     * Returns nutritional values for 7 days starting from given date and going
     * back
     *
     * @param time last day of the observation period.
     * @return list of nutritional values for dates, starting from date given in
     * parameters and going back.
     */
    public ArrayList<HashMap<NutritionalComponent, Double>> weekBeforeDate(Date time) {
        ArrayList<HashMap<NutritionalComponent, Double>> listForWeek
                = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.roll(Calendar.MINUTE, 60);
        cal.roll(Calendar.MILLISECOND, 60000);
        int startDay = cal.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < 7; i++) {
            int day = startDay - i;
            cal.set(Calendar.DAY_OF_YEAR, day);
            listForWeek.add(this.dailyScore(new Date(cal.getTimeInMillis())));
        }
        return listForWeek;
    }

    /**
     * list of Double values for week going back from the date given
     *
     * @param time last day of the observation period
     * @return list of calorie values for week
     */
    public ArrayList<Double> getWeeksCalories(Date time) {
        ArrayList<Double> values = new ArrayList<>();
        NutritionalComponentStructure ncs = NutritionalComponentStructure.getNutrititonalComponentStructure();
        this.weekBeforeDate(time).stream().forEach((HashMap<NutritionalComponent, Double> map) -> {
            Double d = map.get(ncs.getNutCompByName("ENERC"));
            if (d != null) {
                values.add((d / 4.1868));
            } else {
                values.add(0.0);
            }
        });
        return values;
    }

    /**
     * Complete nutritional values for given date.
     *
     * @param time Day that is used
     * @return data structure containing information of that days nutritional
     * values
     */
    public HashMap<NutritionalComponent, Double> dailyScore(Date time) {
        HashMap<NutritionalComponent, Double> dailyScore = new HashMap<>();
        this.mealList.stream().forEach((Meal meal) -> {
            if (equalDayOfDate(time, meal.getTime())) {
                HashMap<NutritionalComponent, Double> ncMap = meal.getTotalNutritionalValues();
                ncMap.entrySet().forEach((Entry<NutritionalComponent, Double> e) -> {
                    Double oldValue = dailyScore.put(e.getKey(), e.getValue());
                    if (oldValue != null) {
                        dailyScore.put(e.getKey(), e.getValue() + oldValue);
                    }
                });
            }
        });
        return dailyScore;
    }

    private boolean equalDayOfDate(Date time, Date time2) {
        Calendar cl1 = Calendar.getInstance();
        cl1.setTime(time);
        Calendar cl2 = Calendar.getInstance();
        cl2.setTime(time2);
        return (cl1.get(Calendar.YEAR) == cl2.get(Calendar.YEAR)
                && cl1.get(Calendar.DAY_OF_YEAR)
                == cl2.get(Calendar.DAY_OF_YEAR));

    }

    /**
     * Hashes string with md5
     *
     * @param string String to be hashed
     * @return Hashed string
     */
    public static String md5Hash(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                md.update(string.getBytes("UTF-8"));
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                //TODO implement salt
                String hashText = bigInt.toString(16);
                return hashText;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
