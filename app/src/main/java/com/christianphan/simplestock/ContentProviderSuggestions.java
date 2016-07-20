package com.christianphan.simplestock;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by christian on 7/7/16.
 */
public class ContentProviderSuggestions extends ContentProvider {

    private static final String AUTH = "com.christianphan.simplestock.ContentProviderSuggestions";
    private static final Uri SUGGESTIONS_URI = Uri.parse("content://" + AUTH + "/" + DataBaseReader.TABLE_NAME);

    final static int SUGGESTIONS = 1;
    final static int SUGGESTIONS2 = 2;
    final static int SUGGESTIONS3 = 3;
    final static int SUGGESTIONS4 = 4;
    final static int SUGGESTIONS5 = 5;




    SQLiteDatabase db;
    DataBaseReader dbhelper;

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
        dbhelper = new DataBaseReader(getContext());

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTH, DataBaseReader.TABLE_NAME, SUGGESTIONS);
        uriMatcher.addURI(AUTH, DataBaseReader.TABLE_NAME + "/#", SUGGESTIONS2);
        // For suggestions table
        uriMatcher.addURI(AUTH,DataBaseReader.TABLE_NAME + "/" + SearchManager.SUGGEST_URI_PATH_QUERY,
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
            db.insert(DataBaseReader.TABLE_NAME,null, values);
        }
        db.close();

        getContext().getContentResolver().notifyChange(uri, null);

        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        String test = "";


        test = uri.getLastPathSegment();


        int uriType = uriMatcher.match(uri);



        MatrixCursor cursor2 = new MatrixCursor(
                new String[] {
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_TEXT_2,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA
                }
        );



        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://autoc.finance.yahoo.com/autoc?query=" + test + "&region=NA&lang=en")
                .build();


        try {
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            JSONObject object= new JSONObject(jsonString);
            JSONObject object2 = object.getJSONObject("ResultSet");
            JSONArray locArr = object2.getJSONArray("Result");


                    if(locArr.length()> 0) {


                        for(int i = 0; i < locArr.length(); i++)
                        {
                            JSONObject ResultObject = locArr.getJSONObject(i);
                            String symbol = ResultObject.getString("symbol");
                            String name = ResultObject.getString("name");
                            if(symbol.length() < 7)
                            {
                                cursor2.addRow(new Object[]{ i, symbol, name, symbol });
                            }
                        }


                    }








            } catch (Exception e) {
            e.printStackTrace();
        }


        return cursor2;
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

