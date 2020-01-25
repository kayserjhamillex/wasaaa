package com.example.aplicacion01.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.aplicacion01.conf.Settings;



import android.content.Context;
import android.provider.BaseColumns;

import com.example.aplicacion01.conf.Settings;
public class DatabaseHelper extends SQLiteOpenHelper{


    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CONTACT_SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CONTACT_SQL_DELETE_ENTRIES);
        onCreate(db);

    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }
        public static class Columns implements BaseColumns {
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
    public static final String CONTACT_TABLE_NAME = "aromas_contact";

    private static final String CONTACT_SQL_CREATE_TABLE =
            "CREATE TABLE " + CONTACT_TABLE_NAME + " (" +
                    Columns._ID + " INTEGER PRIMARY KEY," +
                    Columns.COLUMN_NAME_NAME + " TEXT, " +
                    Columns.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String CONTACT_SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, Settings.DATABASE_NAME, null, Settings.DATABASE_VERSION);
    }
}