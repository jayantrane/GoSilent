package com.fourstars.gosilent.gosilent.checklocation;

/**
 * Created by Jayant on 28-06-2017.
 */

public class Point {
    double x, y;

    Point()
    {}

    Point(double p, double q)
    {
        x = p;
        y = q;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
