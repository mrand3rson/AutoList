package com.example.autolist;

import android.app.Application;

import com.example.autolist.pattern.models.DaoSession;
import com.example.autolist.tools.InitEngine;

/**
 * Created by Andrei on 06.08.2018.
 */

public class App extends Application {

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
    private static DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();

        InitEngine.init(this);
        mDaoSession = InitEngine.initDaoSession(this);
    }
}
