package com.example.usernamelogin.workout_program.to_edit_reps_weight;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ExerciseRepository {
        public static List<Modelclass_forexercises> getExercises(Context context) {
            String json = JsonUtils.loadJSONFromAsset(context, "exercises.json");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Modelclass_forexercises>>() {}.getType();
            return gson.fromJson(json, listType);
        }
    }

