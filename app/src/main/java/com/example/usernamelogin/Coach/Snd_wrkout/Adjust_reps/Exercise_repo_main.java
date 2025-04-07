package com.example.usernamelogin.Coach.Snd_wrkout.Adjust_reps;

import android.content.Context;

import com.google.android.gms.common.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Exercise_repo_main {
    public static List<ModelClass_forexercises_main> getExercises(Context context) {
        String json = JsonUtils_main.loadJSONFromAsset(context,"exercises.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ModelClass_forexercises_main>>() {}.getType();
        return gson.fromJson(json, listType);
    }
}
