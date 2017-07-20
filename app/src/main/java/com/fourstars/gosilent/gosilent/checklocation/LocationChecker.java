package com.fourstars.gosilent.gosilent.checklocation;

import com.fourstars.gosilent.gosilent.databaseanddao.DatabaseHandler;
import com.fourstars.gosilent.gosilent.databaseanddao.LocationBox;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;

/**
 * Created by Jayant on 28-06-2017.
 */

public  class LocationChecker {
    private DatabaseHandler databaseHandler;
    private static LocationBox mlocationBox;
    private static LatLng mlocation;
    private int id;
    private int count;

    public LocationChecker(LocationBox locationBox, LatLng mylocation) {
        mlocationBox = locationBox;
        mlocation = mylocation;
    }

    public boolean checkif() {

        Point point[] =new Point[4];
        Point mypoint = new Point();
        mypoint.x= mlocation.latitude;
        mypoint.y= mlocation.longitude;

        String str1[] = mlocationBox.getPoint1().split(",");
        String str2[] = mlocationBox.getPoint2().split(",");
        String str3[] = mlocationBox.getPoint3().split(",");
        String str4[] = mlocationBox.getPoint4().split(",");

        String[] str = joinArrayGeneric(str1,str2,str3,str4);

        point[0] = new Point(Double.parseDouble(str[0]),Double.parseDouble(str[1]));
        point[1] = new Point(Double.parseDouble(str[2]),Double.parseDouble(str[3]));
        point[2] = new Point(Double.parseDouble(str[4]),Double.parseDouble(str[5]));
        point[3] = new Point(Double.parseDouble(str[6]),Double.parseDouble(str[7]));

        count=0;
  /*      for(int i=0 ; i<4; i++){
            point[i].setX(Double.parseDouble(str[count]));
            count++;
            point[i].setY(Double.parseDouble(str[count]));
            count++;

        }*/

        Point polygon[] = { point[0],point[1],point[2],point[3]};
        PositionWRTPolygon check= new PositionWRTPolygon();
        boolean ifinside =check.isInside(polygon,4,mypoint);

        return ifinside;


    }
    static <T> T[] joinArrayGeneric(T[]... arrays) {
        int length = 0;
        for (T[] array : arrays) {
            length += array.length;
        }

        //T[] result = new T[length];
        final T[] result = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), length);

        int offset = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }

}