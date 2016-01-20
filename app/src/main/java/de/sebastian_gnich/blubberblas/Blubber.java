package de.sebastian_gnich.blubberblas;

import android.graphics.Paint;

/**
 * Created by GnichS on 15.12.2015.
 */
public class Blubber {

    private float positionX, positionY;
    private Paint paint;
    private int radius, speed;

    public Blubber(Paint paint, int radius, int speed) {

        this.paint = paint;
        this.radius = radius;
        this.speed = speed;

        this.positionX = -999;
        this.positionY = -999;

    }

    public float getPositionX() {
        return positionX;
    }

    public Blubber setPositionX(float positionX) {
        this.positionX = positionX;
        return this;
    }

    public float getPositionY() {
        return positionY;
    }

    public Blubber setPositionY(float positionY) {
        this.positionY = positionY;
        return this;
    }

    public Paint getPaint() {
        return paint;
    }

    public Blubber setPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    public int getRadius() {
        return radius;
    }

    public Blubber setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public Blubber setSpeed(int speed) {
        this.speed = speed;
        return this;
    }
}
