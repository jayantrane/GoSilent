package com.fourstars.gosilent.gosilent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayant on 28-06-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationBoxDb";
    private static final String TABLE_LOCATIONBOX = "t_locationBox";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINT1 = "point1";
    private static final String KEY_POINT2 = "point2";
    private static final String KEY_POINT3 = "point3";
    private static final String KEY_POINT4 = "point4";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOCATIONBOX_TABLE = "CREATE TABLE " + TABLE_LOCATIONBOX + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_POINT1 + " TEXT,"
                + KEY_POINT2 + " TEXT," + KEY_POINT3 + " TEXT,"+ KEY_POINT4 + " TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_LOCATIONBOX_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONBOX);
        onCreate(sqLiteDatabase);
    }

    public void save(LocationBox locationBox){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME, locationBox.getName());
        values.put(KEY_POINT1, locationBox.getPoint1());
        values.put(KEY_POINT2, locationBox.getPoint2());
        values.put(KEY_POINT3, locationBox.getPoint3());
        values.put(KEY_POINT4, locationBox.getPoint4());


        db.insert(TABLE_LOCATIONBOX, null, values);
        db.close();
    }

    public LocationBox findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_LOCATIONBOX, new String[]{KEY_ID,KEY_NAME,KEY_POINT1,KEY_POINT2,KEY_POINT3,KEY_POINT4},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return new LocationBox(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
    }

    public List<LocationBox> findAll(){
        List<LocationBox> listLocationBox=new ArrayList<LocationBox>();
        String query="SELECT * FROM "+TABLE_LOCATIONBOX;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                LocationBox locationBox=new LocationBox();
                locationBox.setId(Integer.valueOf(cursor.getString(0)));
                locationBox.setName(cursor.getString(1));
                locationBox.setPoint1(cursor.getString(2));
                locationBox.setPoint2(cursor.getString(3));
                locationBox.setPoint3(cursor.getString(4));
                locationBox.setPoint4(cursor.getString(5));
                listLocationBox.add(locationBox);
            }while(cursor.moveToNext());
        }

        return listLocationBox;
    }

    /* Yet to be implemented....

    public void update(LocationBox LocationBox){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME , LocationBox.getName());
        values.put(KEY_COUNTRY, LocationBox.getCountry());

        db.update(TABLE_LocationBox, values, KEY_ID+"=?", new String[]{String.valueOf(LocationBox.getId())});
        db.close();
    }

    */

    public void delete(LocationBox LocationBox){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_LOCATIONBOX, KEY_ID+"=?", new String[]{String.valueOf(LocationBox.getId())});
        db.close();
    }

    public int getIdCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int cnt  =(int) DatabaseUtils.queryNumEntries(db, TABLE_LOCATIONBOX);
        db.close();
        return cnt;
    }

    public ArrayList<ArrayList<Object>> listIitems() {

        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;
        try {
            SQLiteDatabase db=this.getReadableDatabase();
            cur = db.query(TABLE_LOCATIONBOX, new String[] {KEY_ID, KEY_NAME, KEY_POINT1 },
                    null, null, null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataArray.add(dataList);
                } while (cur.moveToNext());

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
        }
        return dataArray;
    }


}
