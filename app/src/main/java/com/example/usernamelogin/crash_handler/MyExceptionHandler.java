package com.example.usernamelogin.crash_handler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import androidx.annotation.NonNull;

public class MyExceptionHandler implements Thread.UncaughtExceptionHandler{
    private final Context context;

    public MyExceptionHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

     SharedPreferences preferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
     preferences.edit().clear().apply();

        // Delay to ensure the home screen shows first
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }, 500);
    }
}
