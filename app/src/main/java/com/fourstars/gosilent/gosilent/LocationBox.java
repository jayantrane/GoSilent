package com.fourstars.gosilent.gosilent;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jayant on 28-06-2017.
 */

public class LocationBox {
    private int id;
    private String name;
    private String point1;
    private String point2;
    private String point3;
    private String point4;

    public LocationBox() {

    }

    public LocationBox(int id, String name, String point1, String point2, String point3, String point4) {
        this.id = id;
        this.name = name;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoint1() {
        return point1;
    }

    public void setPoint1(String point1) {
        this.point1 = point1;
    }

    public String getPoint2() {
        return point2;
    }

    public void setPoint2(String point2) {
        this.point2 = point2;
    }

    public String getPoint3() {
        return point3;
    }

    public void setPoint3(String point3) {
        this.point3 = point3;
    }

    public String getPoint4() {
        return point4;
    }

    public void setPoint4(String point4) {
        this.point4 = point4;
    }
}