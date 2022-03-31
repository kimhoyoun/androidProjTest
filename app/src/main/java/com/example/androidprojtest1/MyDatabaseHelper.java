package com.example.androidprojtest1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(Context context){
        super(context, "projDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"communityItem\" (\n" +
                "\t\"no\"\tINTEGER NOT NULL,\n" +
                "\t\"user_id\"\tTEXT NOT NULL,\n" +
                "\t\"title\"\tTEXT NOT NULL,\n" +
                "\t\"main_text\"\tTEXT NOT NULL,\n" +
                "\t\"likeNum\"\tINTEGER NOT NULL DEFAULT 0,\n" +
                "\t\"cName\"\tINTEGER NOT NULL DEFAULT 001,\n" +
                "\t\"ctime\"\tREAL NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "\tPRIMARY KEY(\"no\")\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists communityItem");
        onCreate(db);
    }
}
