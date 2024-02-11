package com.example.all_in_onecalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

public class DatabaseManager extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "CalculatorOperations.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "all_operations";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_OP = "calc_operation";
    private static final String COLUMN_SEC = "calc_section";
    private static final String COLUMN_FAV = "calc_favorite";

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OP + " TEXT, " +
                COLUMN_SEC + " TEXT, " +
                COLUMN_FAV + " INTEGER);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (!(Objects.isNull(db))) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void addOperation(String operation, String section, int favorite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_OP, operation);
        cv.put(COLUMN_SEC, section);
        cv.put(COLUMN_FAV, favorite);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
