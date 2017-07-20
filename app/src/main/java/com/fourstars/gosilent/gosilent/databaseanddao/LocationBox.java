package com.fourstars.gosilent.gosilent.databaseanddao;

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
    private String mode;
    private String status;

    public LocationBox() {

    }

    public LocationBox(int id, String name, String point1, String point2, String point3, String point4, String mode, String status) {
        this.id = id;
        this.name = name;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        this.mode = mode;
        this.status = status;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "LocationBox{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", point1='" + point1 + '\'' +
                ", point2='" + point2 + '\'' +
                ", point3='" + point3 + '\'' +
                ", point4='" + point4 + '\'' +
                ", mode='" + mode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}