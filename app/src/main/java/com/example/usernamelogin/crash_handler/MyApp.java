package com.example.usernamelogin.crash_handler;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
    }

}
