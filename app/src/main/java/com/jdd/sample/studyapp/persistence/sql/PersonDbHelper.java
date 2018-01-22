package com.jdd.sample.studyapp.persistence.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author lc. 2018-01-22 13:34
 * @since 0.5.1
 */

public class PersonDbHelper extends SQLiteOpenHelper {

    private static final String DB_FILE_NAME = "person.db";

    private static final int VERSION_1 = 1;

    public PersonDbHelper(Context context) {
        this(context, DB_FILE_NAME, null, VERSION_1);
    }

    public PersonDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbContants.TablePerson.TABLE_NAME + "("
                + DbContants.TablePerson.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DbContants.TablePerson.COLUMN_NAME + " TEXT NOT NULL DEFAULT '',"
                + DbContants.TablePerson.COLUMN_AGE + " INTEGER NOT NULL DEFAULT(0))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
