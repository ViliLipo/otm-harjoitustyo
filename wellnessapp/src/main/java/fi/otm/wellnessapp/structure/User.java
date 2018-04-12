/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
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

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setCalorieGoal(int goal) {
        this.dailyCalorieGoal = goal;
    }

    public int getDailyCalorieGoal() {
        return this.dailyCalorieGoal;
    }

    public void setDailyKiloJouleGoal(double value) {
        this.setCalorieGoal((int) Math.floor((value / 4.1868) + 0.5d));
    }

    public double getDailyKiloJouleGoal() {
        return (double) this.dailyCalorieGoal * 4.1868;
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

    public ArrayList<Meal> getMealList() {
        return this.mealList;
    }

    public void addMeal(Meal meal) {
        this.mealList.add(meal);
    }

    public ArrayList<HashMap<NutritionalComponent, Double>> weekBeforeDate(Date time) {
        ArrayList<HashMap<NutritionalComponent, Double>> listForWeek
                = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.roll(Calendar.MINUTE, 60);
        cal.roll(Calendar.MILLISECOND, 60000);
        int startDay = cal.get(Calendar.DAY_OF_YEAR);
        int startYear = cal.get(Calendar.YEAR);
        for (int i = 0; i < 8; i++) {
            int day = startDay - i;
            cal.set(Calendar.DAY_OF_YEAR, day);
            listForWeek.add(this.dailyScore(new Date(cal.getTimeInMillis())));
        }
        return listForWeek;
    }

    public HashMap<NutritionalComponent, Double> dailyScore(Date time) {
        HashMap<NutritionalComponent, Double> dailyScore = new HashMap<>();

        this.mealList.stream().forEach((Meal meal) -> {
            if (equalDayOfDate(time, meal.getTime())) {
                HashMap<NutritionalComponent, Double> ncMap = meal.getTotalNutritionalValues();
                ncMap.keySet().parallelStream().forEach((NutritionalComponent nc) -> {
                    Double value = ncMap.get(nc);
                    Double oldValue = dailyScore.put(nc, value);
                    if (oldValue != null) {
                        dailyScore.put(nc, value + oldValue);
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

}
