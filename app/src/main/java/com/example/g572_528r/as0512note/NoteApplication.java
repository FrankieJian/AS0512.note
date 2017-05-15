package com.example.g572_528r.as0512note;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by yls on 2017/5/11.
 */

public class NoteApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "d40d2f6f734368cdf2676c983e46d163");
    }
}
