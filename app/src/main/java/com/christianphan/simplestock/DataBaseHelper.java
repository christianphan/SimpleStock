package com.christianphan.simplestock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by christian on 6/16/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "STOCKLIST.db";
    public static final String TABLE_NAME = "STOCK_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "INDEXNAME";
    public static final String COL_3 = "NAME";
    public static final String COL_4 = "PRICE";
    public static final String COL_5 = "PERCENT";
    public static final String COL_6 = "COLOR";
    public static final String COL_7 = "CHANGE";
    public static final String COL_8 = "OPEN";
    public static final String COL_9 = "HIGH";
    public static final String COL_10 = "LOW";
    public static final String COL_11 = "VOLUME";
    public static final String COL_12 = "ANNUAL";
    public static final String COL_13 = "TIME";
    public static final String COL_14 = "NUMOFSHARES";  //res.getString(13)
    public static final String COL_15 = "VALUEOFSHARES"; //res.getString(14)


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT,"
                    + COL_3 + " TEXT," + COL_4 + " TEXT," + COL_5 + " TEXT," + COL_6 + " TEXT," +
                    COL_7 + " TEXT," + COL_8 + " TEXT," + COL_9 + " TEXT," +
                    COL_10 + " TEXT," +  COL_11 + " TEXT," + COL_12 + " TEXT," +
                    COL_13 + " TEXT," + COL_14 + " TEXT," +
                    COL_15 + " TEXT" +  ")";

            db.execSQL(CREATE_CONTACTS_TABLE);

        }
        catch(Exception e)
        {

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String indexName, String name, String price, String percent, String color, String change, String open,
                              String high, String low, String volume, String annual, String time, String sharesown, String valueofshares)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, indexName);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, percent);
        contentValues.put(COL_6, color);
        contentValues.put(COL_7, change);
        contentValues.put(COL_8, open);
        contentValues.put(COL_9, high);
        contentValues.put(COL_10, low);
        contentValues.put(COL_11, volume);
        contentValues.put(COL_12, annual);
        contentValues.put(COL_13, time);
        contentValues.put(COL_14, sharesown);
        contentValues.put(COL_15, valueofshares);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }



    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return cnt;
    }

    public boolean updateData(String id, String indexName, String name, String price, String percent, String color, String change, String open,
                              String high, String low, String volume, String annual, String time, String sharesown, String valueofshares)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, indexName);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, percent);
        contentValues.put(COL_6, color);
        contentValues.put(COL_7, change);
        contentValues.put(COL_8, open);
        contentValues.put(COL_9, high);
        contentValues.put(COL_10, low);
        contentValues.put(COL_11, volume);
        contentValues.put(COL_12, annual);
        contentValues.put(COL_13, time);
        contentValues.put(COL_14, sharesown);
        contentValues.put(COL_15, valueofshares);

        db.update(TABLE_NAME ,contentValues,"ID = ?",new String[] { id });
        return true;

    }


    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[] { id });

    }

    public Cursor getLastData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToLast();
        return res;


    }


    public Cursor getDataList(String text)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[] {COL_1, COL_2 ,COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9, COL_10, COL_11, COL_12, COL_13},
                COL_2 +" LIKE '"+ text +"%'", null, null, null, null);
    }
}


