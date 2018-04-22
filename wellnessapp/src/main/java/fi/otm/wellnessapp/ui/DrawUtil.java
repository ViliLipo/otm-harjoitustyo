/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.ui;

import fi.otm.wellnessapp.structure.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author vililipo
 */
public class DrawUtil {

    private Canvas canvas;
    private User user;
    private int xmargin;

    public DrawUtil(Canvas canvas, User user) {
        this.canvas = canvas;
        this.user = user;
        this.xmargin = 40;
    }

    public void drawDiagram(Date start) {
        this.canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.drawBars(start);
        this.drawScale();
        this.drawYAxis(this.canvas.getGraphicsContext2D());
        this.drawBaseLine(this.canvas.getGraphicsContext2D());
        this.drawGoal(this.canvas.getGraphicsContext2D());
    }

    private void drawBars(Date start) {
        int i = 0;
        ArrayList<Double> weeksCalories = this.user.getWeeksCalories(start);
        //System.out.println(weeksCalories);
        Calendar cl = Calendar.getInstance();
        cl.setTime(start);
        cl.add(Calendar.DAY_OF_MONTH, -6);
        for (int j = 6; j >= 0; j--) {
            Double d = weeksCalories.get(j);
            int x = xmargin + 5 + i * this.calcSegmentSizeX();
            this.drawOneBar(this.canvas.getGraphicsContext2D(), d, x, cl.getTime());
            i++;
            cl.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void drawScale() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setStroke(Color.INDIANRED);
        gc.setLineWidth(1);
        for (int i = 1; i < 9; i++) {
            double y = i * (canvas.getHeight() / 10);
            gc.strokeLine(xmargin, y, canvas.getWidth(), y);
            double linevalue = Math.abs(y - this.getBaseLine()) * this.scaleCaloriePerPixel();
            gc.strokeText(String.format("%.0f", linevalue), 0, y);
        }

    }

    private void drawOneBar(GraphicsContext gc, Double calories, int start, Date d) {
        if (calories != null) {
            pickColor(gc, calories);
            int width = this.calcSegmentSizeX() / 3;
            Double height = calories / this.scaleCaloriePerPixel();
            //System.out.println("DEBUG: drawOneBar height -> " + height);
            int x = start + width;
            double y = this.getBaseLine() - height;
            //System.out.println("DEBUG: drawOneBar y -> " + y);
            gc.fillRect(x, y, width, height);
        }
        int x2 = start + this.calcSegmentSizeX() / 3;
        gc.setStroke(Color.BLACK);
        String date = new SimpleDateFormat("dd.MM").format(d);
        gc.strokeText(date, x2, this.getBaseLine() + 15);
    }

    private void pickColor(GraphicsContext gc, Double calories) {
        if (Math.abs(user.getDailyCalorieGoal() - calories) > user.getDailyCalorieGoal() * 0.4d) {
            gc.setFill(Color.RED);
        } else if (Math.abs(user.getDailyCalorieGoal() - calories) > user.getDailyCalorieGoal() * 0.2d) {
            gc.setFill(Color.ORANGE);
        } else {
            gc.setFill(Color.LIMEGREEN);
        }
    }

    public int calcSegmentSizeX() {
        return (int) Math.floor((this.canvas.getWidth() - 2 * xmargin) / 7);
    }

    public double calorieCeiling() {
        return this.user.getDailyCalorieGoal() * 1.8d;
    }

    public int scaleCaloriePerPixel() {
        int scale = (int) Math.ceil(this.calorieCeiling() / this.canvas.getHeight());
        return scale;
    }

    public double getBaseLine() {
        return this.canvas.getHeight() - this.canvas.getHeight() / 10;
    }

    private void drawBaseLine(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0d);
        gc.strokeLine(xmargin, this.getBaseLine(), this.canvas.getWidth(),
                this.getBaseLine());
    }

    private void drawYAxis(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0d);
        gc.strokeLine(xmargin, this.getBaseLine(), xmargin, 0);
    }

    private void drawGoal(GraphicsContext gc) {
        gc.setStroke(Color.GOLD);
        gc.setLineWidth(1.6d);
        Double y = this.getBaseLine()
                - this.user.getDailyCalorieGoal() / this.scaleCaloriePerPixel();
        gc.strokeLine(xmargin + 2, y, this.canvas.getWidth(), y);
    }

}
