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

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT,"
                    + COL_3 + " TEXT," + COL_4 + " TEXT," + COL_5 + " TEXT," + COL_6 + " TEXT" + ")";

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


    public boolean insertData(String indexName, String name, String price, String percent, String color)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, indexName);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, percent);
        contentValues.put(COL_6, color);

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

    public boolean updateData(String id, String indexName, String name, String price, String percent, String color)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, indexName);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, percent);
        contentValues.put(COL_6, color);
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
}


