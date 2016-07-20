package com.christianphan.simplestock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 7/5/16.
 */
public class CSVDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SEARCHLIST.db";
    public static final String TABLE_NAME = "SEARCH_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "INDEXNAME";
    public static final String COL_3 = "NAME";

    public CSVDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT,"
                    + COL_3 +  " TEXT" + ")";

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


    public boolean insertData(String indexName, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, indexName);
        contentValues.put(COL_3, name);

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

    public boolean updateData(String id, String indexName, String name)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, indexName);
        contentValues.put(COL_3, name);

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

    public Cursor getSuggestions(String text)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_2, COL_3},
                COL_2 +" LIKE '"+ text +"%'", null, null,null , null, "5");
        return cursor;
    }


    public Cursor getLastData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToLast();
        return res;


    }



    //returns List of MySuggestions with the searchTerm
    public Cursor read(String searchTerm)
    {


        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_NAME;
        sql += " WHERE " + COL_3 + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + COL_1 + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getReadableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }






}
