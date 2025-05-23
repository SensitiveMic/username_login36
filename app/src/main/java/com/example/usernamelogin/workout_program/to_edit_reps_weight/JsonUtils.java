package com.example.usernamelogin.workout_program.to_edit_reps_weight;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}