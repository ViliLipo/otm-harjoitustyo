/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.ui;

import fi.otm.wellnessapp.structure.User;
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

    public DrawUtil(Canvas canvas, User user) {
        this.canvas = canvas;
        this.user = user;
    }

    public void drawDiagram(Date start) {
        this.drawScale();
        this.drawOneBar(this.canvas.getGraphicsContext2D(), 2000.d, 50);
    }
    
    public void drawScale() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setStroke(Color.AQUA);
        gc.setLineWidth(1);
        int xmargin = 20;
        for(int i = 0; i < 10; i++ ) {
            double y = i * (canvas.getHeight() / 10);
            gc.strokeLine(xmargin, y , canvas.getWidth(), y);
        }
        
    }

    public void drawOneBar(GraphicsContext gc, Double calories, int start) {
        gc.setFill(Color.GREEN);
        int width = this.calcSegmentSizeX() / 5;
        start += 2 * width;
        Double height = calories / this.scaleCaloriePerPixel();
        double x = start;
        double y = (-1) * (height - this.canvas.getHeight());
        gc.fillRect(x, y, width, height);

    }

    public int calcSegmentSizeX() {
        return (int) Math.floor(this.canvas.getWidth() / 7);
    }

    public double calorieCeiling() {
        return this.user.getDailyCalorieGoal() * 1.4d;
    }

    public int scaleCaloriePerPixel() {
        int scale = (int) Math.ceil(this.calorieCeiling() / this.canvas.getHeight());
        return scale;
    }

    public double getBaseLine() {
        return this.canvas.getHeight() - this.canvas.getHeight() / 10;
    }

}
