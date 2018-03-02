package com.yjnull.cameragoogle.base;

import android.app.Application;

import com.yjnull.cameragoogle.camera.chapter3.CrashHandler;

/**
 * Created by yangy on 2018/3/2.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);
    }
}
