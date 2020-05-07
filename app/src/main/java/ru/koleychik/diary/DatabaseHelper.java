package ru.koleychik.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "main_db_diary.db"; // название бд
    public static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE_TEXT = "password"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_TEXT = "text";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_TEXT + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE
                + " TEXT, " + COLUMN_THEME + " TEXT, " + COLUMN_NUMBER + " INTEGER, " + COLUMN_TEXT + " TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TEXT);
        onCreate(db);
    }

}
