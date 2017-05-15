package com.example.g572_528r.as0512note.date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by g572-528r on 2017/5/15.
 */
public class NoteOpenHelper extends SQLiteOpenHelper{
    private  static NoteOpenHelper mInstance;
    private  final  String CREATE_TABLE_NOTE = "create table note (id int, title varchar(40), content text, createtime date, modifytime date, isdel int default 0);";

    public NoteOpenHelper(Context context) {
        super(context, "notedb", null, 1);
    }

    public static NoteOpenHelper getmInstance(Context context){
        if(mInstance == null){
            mInstance = new NoteOpenHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion ==3){
            // alter tabble note add year date, health, weather varchar(10);
        }

        if(oldVersion == 2 && newVersion ==3){
            // alter tabble note add weather varchar(10);
        }
    }
}
