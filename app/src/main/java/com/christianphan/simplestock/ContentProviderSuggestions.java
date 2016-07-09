package com.christianphan.simplestock;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.HashMap;


/**
 * Created by christian on 7/7/16.
 */
public class ContentProviderSuggestions extends ContentProvider {

    private static final String AUTH = "com.christianphan.simplestock.ContentProviderSuggestions";
    private static final Uri SUGGESTIONS_URI = Uri.parse("content://" + AUTH + "/" + CSVDatabase.TABLE_NAME);

    final static int SUGGESTIONS = 1;
    final static int SUGGESTIONS2 = 2;
    final static int SUGGESTIONS3 = 3;
    final static int SUGGESTIONS4 = 4;
    final static int SUGGESTIONS5 = 5;




    SQLiteDatabase db;
    CSVDatabase dbhelper;

    private  UriMatcher uriMatcher;

    private static final String[] SEARCH_SUGGEST_COLUMNS = {
            BaseColumns._ID,

            //First Column mandatory
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_TEXT_2,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
    };



    @Override
    public boolean onCreate()
    {
        dbhelper = new CSVDatabase(getContext());

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTH, CSVDatabase.TABLE_NAME, SUGGESTIONS);
        uriMatcher.addURI(AUTH, CSVDatabase.TABLE_NAME + "/#", SUGGESTIONS2);
        // For suggestions table
        uriMatcher.addURI(AUTH,CSVDatabase.TABLE_NAME + "/" + SearchManager.SUGGEST_URI_PATH_QUERY,
                SUGGESTIONS3);
        uriMatcher.addURI(AUTH,SearchManager.SUGGEST_URI_PATH_QUERY,SUGGESTIONS4);
        uriMatcher.addURI(AUTH, "/search_suggest_query/" , SUGGESTIONS5);

        return true;
    }

    @Override
    public Uri insert (Uri uri, ContentValues values)
    {
        dbhelper.getWritableDatabase();

        if(uriMatcher.match(uri) == SUGGESTIONS)
        {
            db.insert(CSVDatabase.TABLE_NAME,null, values);
        }
        db.close();

        getContext().getContentResolver().notifyChange(uri, null);

        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CSVDatabase.TABLE_NAME);
        String tester = SearchManager.SUGGEST_URI_PATH_QUERY.toString();
        String test = "";


        test = uri.getLastPathSegment();


        int uriType = uriMatcher.match(uri);


        HashMap<String, String> columnMap = new HashMap<String, String>();
        columnMap.put(BaseColumns._ID, CSVDatabase.COL_1 + " AS " + BaseColumns._ID);
        columnMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, CSVDatabase.COL_2 + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        columnMap.put(SearchManager.SUGGEST_COLUMN_TEXT_2 , CSVDatabase.COL_3 + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        columnMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA, CSVDatabase.COL_2 + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA);
        queryBuilder.setProjectionMap(columnMap);

        dbhelper = new CSVDatabase(getContext());
        //database = dbhelper.getReadableDatabase();

        SQLiteDatabase db2 = dbhelper.getWritableDatabase();


        Cursor cursor;
            cursor = queryBuilder.query(db2, projection, CSVDatabase.COL_2 + " LIKE '" + test + "%'", selectionArgs, null, null, sortOrder, null);

        return cursor;
    }





    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case SUGGESTIONS:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException();
    }


    @Override
    public int delete(Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException();
    }


}

